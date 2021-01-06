package com.pl.restApi.service;

import com.pl.restApi.BaseTransactionUtils;
import com.pl.restApi.dto.MonthTransactionSummaryDto;
import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.mapper.TransactionMapper;
import com.pl.restApi.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("Month summary list with transactions only from one month - should correct calculate summary")
    public void shouldReturnCorrectMonthSummaryValues() {
        List<Transaction> actualTransactionList = prepareTransactionList(
                new Transaction(TRANSACTION_ID_1, TRANSACTION_NAME_1, TRANSACTION_DATE_1, TRANSACTION_AMOUNT_1, TRANSACTION_TYPE_1, TRANSACTION_KIND_1),
                new Transaction(TRANSACTION_ID_2, TRANSACTION_NAME_2, TRANSACTION_DATE_2, TRANSACTION_AMOUNT_2, TRANSACTION_TYPE_2, TRANSACTION_KIND_2)
        );
        when(transactionService.getTransactions()).thenReturn(actualTransactionList);
        when(transactionMapper.mapToTransactionDTOList(actualTransactionList)).thenReturn(prepareTransactionDTOList(actualTransactionList));

        List<MonthTransactionSummaryDto> monthTransactionSummary = transactionSummaryService.summary();

        assertAll("monthSummaryList",
                () -> assertEquals(monthTransactionSummary.size(), 1),
                () -> assertEquals(monthTransactionSummary.get(0).getMonthDate(), YearMonth.of(2020, 10)),
                () -> assertEquals(monthTransactionSummary.get(0).getTransactionList().size(), 2),
                () -> assertEquals(monthTransactionSummary.get(0).getSpendSummary(), BigDecimal.valueOf(11))
        );
    }

    @Test
    @DisplayName("Month summary list with transaction from two months - should correct calculate summary")
    public void shouldReturnCorrectMonthSummaryValues_whenTransactionFromTwoDifferentMonths() {
        List<Transaction> transactionFromFirstMonthList = prepareTransactionList(
                new Transaction(TRANSACTION_ID_1, TRANSACTION_NAME_1, TRANSACTION_DATE_1, TRANSACTION_AMOUNT_1, TRANSACTION_TYPE_1, TRANSACTION_KIND_1),
                new Transaction(TRANSACTION_ID_2, TRANSACTION_NAME_2, TRANSACTION_DATE_2, TRANSACTION_AMOUNT_2, TRANSACTION_TYPE_2, TRANSACTION_KIND_2)
        );
        List<Transaction> transactionFromSecondMonthList = prepareTransactionList(
                new Transaction(TRANSACTION_ID_3, TRANSACTION_NAME_3, TRANSACTION_DATE_3, TRANSACTION_AMOUNT_3, TRANSACTION_TYPE_3, TRANSACTION_KIND_3)
        );
        List<Transaction> actualTransactionList = Stream.concat(transactionFromFirstMonthList.stream(), transactionFromSecondMonthList.stream()).collect(Collectors.toList());

        when(transactionService.getTransactions()).thenReturn(actualTransactionList);
        when(transactionMapper.mapToTransactionDTOList(transactionFromFirstMonthList)).thenReturn(prepareTransactionDTOList(transactionFromFirstMonthList));
        when(transactionMapper.mapToTransactionDTOList(transactionFromSecondMonthList)).thenReturn(prepareTransactionDTOList(transactionFromSecondMonthList));

        List<MonthTransactionSummaryDto> monthTransactionSummaryDtoList = transactionSummaryService.summary();

        assertAll("monthSummaryListFromTwoMonths",
                () -> assertEquals(monthTransactionSummaryDtoList.size(), 2),
                () -> assertEquals(monthTransactionSummaryDtoList.get(0).getMonthDate(), YearMonth.of(2020, 12)),
                () -> assertEquals(monthTransactionSummaryDtoList.get(1).getMonthDate(), YearMonth.of(2020, 10)),
                () -> assertEquals(monthTransactionSummaryDtoList.get(0).getTransactionList().size(), 1),
                () -> assertEquals(monthTransactionSummaryDtoList.get(1).getTransactionList().size(), 2),
                () -> assertEquals(monthTransactionSummaryDtoList.get(0).getSpendSummary(), BigDecimal.valueOf(1221.21)),
                () -> assertEquals(monthTransactionSummaryDtoList.get(1).getSpendSummary(), BigDecimal.valueOf(11))
        );
    }

    @Test
    @DisplayName("Month summary list with transaction from two months - list should be descending order")
    public void shouldReturnDescendingOrderedObjectInList() {
        List<Transaction> transactionFromFirstMonthList = prepareTransactionList(
                new Transaction(TRANSACTION_ID_1, TRANSACTION_NAME_1, TRANSACTION_DATE_1, TRANSACTION_AMOUNT_1, TRANSACTION_TYPE_1, TRANSACTION_KIND_1),
                new Transaction(TRANSACTION_ID_2, TRANSACTION_NAME_2, TRANSACTION_DATE_2, TRANSACTION_AMOUNT_2, TRANSACTION_TYPE_2, TRANSACTION_KIND_2)
        );
        List<Transaction> transactionFromSecondMonthList = prepareTransactionList(
                new Transaction(TRANSACTION_ID_3, TRANSACTION_NAME_3, TRANSACTION_DATE_3, TRANSACTION_AMOUNT_3, TRANSACTION_TYPE_3, TRANSACTION_KIND_3)
        );
        List<Transaction> actualTransactionList = Stream.concat(transactionFromFirstMonthList.stream(), transactionFromSecondMonthList.stream()).collect(Collectors.toList());

        when(transactionService.getTransactions()).thenReturn(actualTransactionList);
        when(transactionMapper.mapToTransactionDTOList(transactionFromFirstMonthList)).thenReturn(prepareTransactionDTOList(transactionFromFirstMonthList));
        when(transactionMapper.mapToTransactionDTOList(transactionFromSecondMonthList)).thenReturn(prepareTransactionDTOList(transactionFromSecondMonthList));

        List<MonthTransactionSummaryDto> monthTransactionSummaryDtoList = transactionSummaryService.summary();

        assertEquals(monthTransactionSummaryDtoList.get(0).getMonthDate(), YearMonth.of(2020, 12));
        assertEquals(monthTransactionSummaryDtoList.get(1).getMonthDate(), YearMonth.of(2020, 10));
    }

    private List<Transaction> prepareTransactionList(Transaction ...transactionsVarargs) {
        List<Transaction> transactionList = new ArrayList<>();
        Collections.addAll(transactionList, transactionsVarargs);
        return transactionList;
    }

    private List<TransactionDto> prepareTransactionDTOList(List<Transaction> transactionList) {
        List<TransactionDto> transactionDTOList = transactionList.stream().map(
                tran -> new TransactionDto(tran.getId().toString(), tran.getTransactionName(), tran.getTransactionDesc(), tran.getTransactionDate(),
                            tran.getTransactionAmount(), tran.getTransactionType().name(), tran.getTransactionKind().name())
            ).collect(Collectors.toList());
        return transactionDTOList;
    }
}
