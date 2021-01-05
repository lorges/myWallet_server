package com.pl.restApi.service;

import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.exception.TransactionNotFoundException;
import com.pl.restApi.model.Transaction;

import java.util.List;

public interface ITransactionService {

    List<Transaction> getTransactions();

    Transaction getTransaction(Long transactionId) throws TransactionNotFoundException;

    Transaction processSaveTransaction(TransactionDto transactionDto);

    /**
     * Method for deleting transaction with given transactionId from database.
     * @param transactionId ID transaction to delete
     * @throws TransactionNotFoundException when transaction with given id not found
     */
    void deleteTransaction(Long transactionId) throws TransactionNotFoundException;

    Transaction editTransaction(Long transactionId, TransactionDto transactionDto) throws TransactionNotFoundException;
}
