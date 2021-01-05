package com.pl.restApi.service;

import com.pl.restApi.BaseTransactionUtils;
import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.exception.TransactionNotFoundException;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.model.enums.TransactionType;
import com.pl.restApi.repository.ITransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


public class TransactionServiceTest extends BaseTransactionUtils {

    @Mock
    ITransactionRepository transactionRepository;

    @InjectMocks
    TransactionService transactionService;

    private List<Transaction> mockTransactionsList = new ArrayList<>();
    private Transaction transaction;
    private TransactionDto transactionDto;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.mockTransactionsList = new ArrayList<>();
        mockTransactionsList.add(new Transaction(1L, "Testowa 1", "Testowe 1 desc",
                LocalDate.of(2020,10,10), BigDecimal.TEN, TransactionType.SPEND, TransactionKind.FOOD, new HashSet<>()));

        mockTransactionsList.add(new Transaction(2L, "Testowa 2", "Testowe 2 desc",
                LocalDate.of(2020,10,10), BigDecimal.valueOf(23.333), TransactionType.SPEND, TransactionKind.FOOD, new HashSet<>()));
        transaction = transaction();
        transactionDto = transactionDto();
    }

    @Test
    void getTransactions_ShouldReturnEmptyListWhenNoTransactions() {
        given(transactionRepository.findAll()).willReturn(Collections.emptyList());

        List<Transaction> transactionList = transactionService.getTransactions();

        assertEquals(0, transactionList.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void getTransactions_ShouldReturnTransactionsList() {
        given(transactionRepository.findAll()).willReturn(mockTransactionsList);

        List<Transaction> transactionList = transactionService.getTransactions();

        assertEquals(mockTransactionsList.size(), transactionList.size());
        assertEquals(mockTransactionsList.get(0),transactionList.get(0));
        assertEquals(mockTransactionsList.get(1),transactionList.get(1));
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void getTransaction_ShouldReturnTransaction() {
        given(transactionRepository.findById(1L)).willReturn(Optional.of(transaction));

        Transaction returnedTransaction = transactionService.getTransaction(1L);

        assertEquals(transaction.getTransactionName(), returnedTransaction.getTransactionName());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void getTransaction_ShouldThrowTransactionNotFoundExceptionWhenTransactionNotFound() {
        given(transactionRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransaction(1L);
        });
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void deleteTransaction_ShouldThrowExceptionWhenTransactionNotExits() {
        assertThrows(TransactionNotFoundException.class, () -> {
                    transactionService.deleteTransaction(1L);
        });
        verify(transactionRepository,times(1)).findById(1L);
        verify(transactionRepository, times(0)).delete(transaction);
    }

    @Test
    void deleteTransaction_ShouldDeleteTransactionIfExits() {
        given(transactionRepository.findById(1L)).willReturn(Optional.of(transaction));
        doNothing().when(transactionRepository).delete(transaction);

        transactionService.deleteTransaction(1L);

        verify(transactionRepository,times(1)).findById(1L);
        verify(transactionRepository, times(1)).delete(transaction);
    }

  /*  @Test
    void processAndSaveTransaction_shouldReturnTransactionDtoWhenSaved() {
        given(transactionRepository.save(transaction)).willReturn(transaction);

        Transaction savedTransaction = transactionService.processSaveTransaction(transaction);

        assertEquals(savedTransaction.getId(), transaction.getId());
        assertEquals(savedTransaction.getTransactionName(), transaction.getTransactionName());
        verify(transactionRepository, times(1)).save(transaction);
    }*/
}
