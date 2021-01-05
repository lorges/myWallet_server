package com.pl.restApi.controller;

import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.exception.TransactionNotFoundException;
import com.pl.restApi.mapper.TransactionMapper;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
@RestController
@RequestMapping(path = "/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;
    private final TransactionMapper transactionMapper;

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
    public TransactionDto saveTransaction(@RequestBody TransactionDto transactionDto) {
        log.info("TransactionController::saveTransaction(?) - {}", transactionDto);
        return asDto(transactionService.processSaveTransaction(transactionDto));
    }

    @DeleteMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable Long transactionId) throws TransactionNotFoundException {
        log.info("TransactionController::deleteTransaction(?) - transactionId = {}", transactionId);
        transactionService.deleteTransaction(transactionId);
    }

    @PutMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto editTransaction(@PathVariable Long transactionId, @RequestBody TransactionDto transactionDto) {
        log.info("TransactionController::editTransaction(?,?) - id = {}, transaction = {}", transactionId, transactionDto);
        return asDto(transactionService.editTransaction(transactionId, transactionDto));
    }

    private List<TransactionDto> asDtoList(List<Transaction> transactionList) {
       return transactionMapper.mapToTransactionDTOList(transactionList);
    }

    private TransactionDto asDto(Transaction transaction) {
        return transactionMapper.toTransactionDto(transaction);
    }
}
