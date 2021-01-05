package com.pl.restApi.service;

import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.exception.TransactionNotFoundException;
import com.pl.restApi.mapper.TransactionMapper;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.repository.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final ITransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public List<Transaction> getTransactions() {
        log.info("TransactionService::getTransactions()");
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransaction(Long transactionId) throws TransactionNotFoundException {
        return transactionRepository.findById(transactionId)
                        .orElseThrow(TransactionNotFoundException::new);
    }

    @Override
    public Transaction processSaveTransaction(TransactionDto transactionDto) {
        log.debug("TransactionService::processSaveTransaction(?) - {}", transactionDto);
        Transaction savedTransaction = save(transactionMapper.toTransaction(transactionDto));
        log.info("TransactionService:: processSaveTransaction(?) - transaction with id = {} correctly persist in db",
                     savedTransaction.getId());
        return savedTransaction;
    }

    private Transaction save(Transaction transactionToSave) {
        return transactionRepository.save(transactionToSave);
    }

    @Override
    public void deleteTransaction(Long transactionId) throws TransactionNotFoundException {
        log.debug("TransactionService::deleteTransaction(?) - deleting transaction with id = {}", transactionId);
        Transaction transaction = getTransaction(transactionId);
        transactionRepository.delete(transaction);
    }

    @Override
    public Transaction editTransaction(Long transactionId, TransactionDto transactionDto) throws TransactionNotFoundException {
        log.debug("TransactionService::editTransaction(?) - editing transaction with id = {} with values = {}",
                transactionId, transactionDto);
        Transaction transactionToEdit = getTransaction(transactionId);
        transactionMapper.updateTransactiontFromDTO(transactionDto, transactionToEdit);
        return this.save(transactionToEdit);
    }
}
