package com.pl.restApi.service;

import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.exception.TransactionNotFoundException;
import com.pl.restApi.mapper.TransactionMapper;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.model.enums.TransactionType;
import com.pl.restApi.repository.ITransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class TransactionService implements ITransactionService {

    private final ITransactionRepository transactionRepository;

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
    public Transaction processSaveTransaction(Transaction transaction) {
        log.debug("TransactionService::processSaveTransaction(?) - {}", transaction);
        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("TransactionService:: processSaveTransaction(?) - transaction with id = {} correctly persist in db",
                     savedTransaction.getId());
        return savedTransaction;
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
        copyEditedValueToTransaction(transactionToEdit, transactionDto);
        return processSaveTransaction(transactionToEdit);
    }

    /**
     * This method copy values from new transactionDTO object to currently edited transactionDTO object.
     * @param transactionToEdit it's currently edited object
     * @param transactionDto it's new dto object comming from controller
     * @return void
     * */
    private void copyEditedValueToTransaction(Transaction transactionToEdit, TransactionDto transactionDto) {
        transactionToEdit.setTransactionName(transactionDto.getTransactionName());
        transactionToEdit.setTransactionAmount(transactionDto.getTransactionAmount());
        transactionToEdit.setTransactionType(TransactionType.valueOf(transactionDto.getTransactionType()));
        transactionToEdit.setTransactionDate(transactionDto.getTransactionDate());
        transactionToEdit.setTransactionDesc(transactionDto.getTransactionDesc());
        transactionToEdit.setTransactionKind(
                null != transactionDto.getTransactionKind()
                ? TransactionKind.valueOf(transactionDto.getTransactionKind())
                : null
        );
    }

    @Override
    public TransactionDto mapToDto(Transaction transaction) {
        return TransactionMapper.INSTANCE.toTransactionDto(transaction);
    }
}
