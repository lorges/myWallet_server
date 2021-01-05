package com.pl.restApi.service;

import com.pl.restApi.BaseTransactionUtils;
import com.pl.restApi.dto.MonthTransactionSummaryDto;
import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.mapper.TransactionMapper;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.model.enums.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.AssertionErrors;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;

public class TransactionSummaryServiceTest extends BaseTransactionUtils {

    @Mock
    ITransactionService transactionService;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    TransactionSummaryService transactionSummaryService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Calculate transactions summary - should return empty summary list when no transactions")
    public void shouldReturnEmptyMapIfTransactionListEmpty() {
        when(transactionService.getTransactions()).thenReturn(Collections.emptyList());

        List<MonthTransactionSummaryDto> monthTransactionSummary = transactionSummaryService.summary();

        Assert.isTrue(monthTransactionSummary.isEmpty(), "Collections not empty");
    }

    @Test
    @DisplayName("Calculate transactions summary - should return correct sum ")
    public void shouldReturnCorrectMonthSummaryValues() {
        when(transactionService.getTransactions()).thenReturn(transactionsToSummaryList());
        when(transactionMapper.mapToTransactionDTOList(transactionsToSummaryList())).thenReturn(transactionsDTOToSummaryList());
        List<MonthTransactionSummaryDto> monthTransactionSummary = transactionSummaryService.summary();

        Assertions.assertEquals(monthTransactionSummary.size(), 1);
        Assertions.assertEquals(monthTransactionSummary.get(0).getTransactionList().size(), 2);
        Assertions.assertEquals(monthTransactionSummary.get(0).getSpendSummary(), new BigDecimal("1033.32"));
    }

    private List<Transaction> transactionsToSummaryList() {
        return List.of(
                Transaction.builder()
                        .transactionAmount(BigDecimal.TEN)
                        .transactionDate(LocalDate.of(2020,10,10))
                        .transactionType(TransactionType.SPEND)
                        .transactionKind(TransactionKind.FOOD)
                        .transactionName("test 1")
                        .id(1L)
                 .build(),
                Transaction.builder()
                        .transactionAmount(new BigDecimal("1023.32"))
                        .transactionDate(LocalDate.of(2020, 10, 15))
                        .transactionType(TransactionType.SPEND)
                        .transactionKind(TransactionKind.FOOD)
                        .transactionName("test 2")
                        .id(2L)
                .build()
        );
    }

    private List<TransactionDto> transactionsDTOToSummaryList() {
        return List.of(
                TransactionDto.builder()
                        .transactionAmount(BigDecimal.TEN)
                        .transactionDate(LocalDate.of(2020,10,10))
                        .transactionType(TransactionType.SPEND.name())
                        .transactionKind(TransactionKind.FOOD.name())
                        .transactionName("test 1")
                        .id("1")
                        .build(),
                TransactionDto.builder()
                        .transactionAmount(new BigDecimal("1023.32"))
                        .transactionDate(LocalDate.of(2020, 10, 15))
                        .transactionType(TransactionType.SPEND.name())
                        .transactionKind(TransactionKind.FOOD.name())
                        .transactionName("test 2")
                        .id("2")
                        .build()
        );
    }

}
