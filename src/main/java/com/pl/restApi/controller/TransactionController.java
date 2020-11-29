package com.pl.restApi.controller;

import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.exception.TransactionNotFoundException;
import com.pl.restApi.mapper.TransactionMapper;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.service.ITransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping(path = "/transactions")
@AllArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDto> getTransactions() {
        log.info("TransactionController::getTransactions()");
        return asDtoList(transactionService.getTransactions());
    }

    @GetMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto getTransaction(@PathVariable Long transactionId) throws TransactionNotFoundException {
        log.info("TransactionController::getTransaction() - transactionId = {}", transactionId);
        return asDto(transactionService.getTransaction(transactionId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction saveTransaction(@RequestBody TransactionDto transactionDto) {
        log.info("TransactionController::saveTransaction(?) - {}", transactionDto);
        return transactionService.processSaveTransaction(asEntity(transactionDto));
    }

    @DeleteMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable Long transactionId) throws TransactionNotFoundException {
        log.info("TransactionController::deleteTransaction(?) - transactionId = {}", transactionId);
        transactionService.deleteTransaction(transactionId);
    }

    @PutMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public Transaction editTransaction(@PathVariable Long transactionId, @RequestBody TransactionDto transactionDto) {
        log.info("TransactionController::editTransaction(?,?) - id = {}, transaction = {}", transactionId, transactionDto);
        return transactionService.editTransaction(transactionId, transactionDto);
    }

    private List<TransactionDto> asDtoList(List<Transaction> transactionList) {
        return transactionList
                .stream()
                .map(TransactionMapper.INSTANCE::toTransactionDto)
                .collect(Collectors.toList());
    }

    private TransactionDto asDto(Transaction transaction) {
        return TransactionMapper.INSTANCE.toTransactionDto(transaction);
    }

    private Transaction asEntity(TransactionDto transactionDto) {
        return TransactionMapper.INSTANCE.toTransaction(transactionDto);
    }
}
