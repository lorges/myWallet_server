package com.pl.restApi.service;

import com.pl.restApi.dto.MonthTransactionSummaryDto;
import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.mapper.TransactionMapper;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransactionSummaryService implements ITransactionSummaryService {

    private final ITransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Override
    public List<MonthTransactionSummaryDto> summary() {
        List<Transaction> transactionList = transactionService.getTransactions();
        Map<YearMonth, List<Transaction>> transactionPerMonthMap = collectMapWithTransactionListPerMonth(transactionList);
        List<MonthTransactionSummaryDto> summaryList = new ArrayList<>();

        for(Map.Entry<YearMonth, List<Transaction>> entry : transactionPerMonthMap.entrySet()) {
            MonthTransactionSummaryDto monthSummary = new MonthTransactionSummaryDto();
            monthSummary.setMonthDate(entry.getKey());
            monthSummary.setTransactionList(asDtoList(entry.getValue()));
            monthSummary.setIncomeSummary(calculateSummaryByTransactionType(entry.getValue(), TransactionType.INCOME));
            monthSummary.setSpendSummary(calculateSummaryByTransactionType(entry.getValue(),  TransactionType.SPEND));

            summaryList.add(monthSummary);
        }
        summaryList = sortByMonthDateDesc(summaryList);
        return summaryList;
    }

    private List<MonthTransactionSummaryDto> sortByMonthDateDesc(List<MonthTransactionSummaryDto> summaryList) {
        return summaryList.stream()
                .sorted(Comparator.comparing(MonthTransactionSummaryDto::getMonthDate)
                    .reversed())
                .collect(Collectors.toList());
    }

    private BigDecimal calculateSummaryByTransactionType(List<Transaction> transactionsList, TransactionType transactionType) {
        return transactionsList.stream()
                .filter(tran -> transactionType.equals(tran.getTransactionType()))
                .map(Transaction::getTransactionAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private Map<YearMonth, List<Transaction>> collectMapWithTransactionListPerMonth(List<Transaction> transactionList) {
        return transactionList.stream()
                .collect(Collectors.groupingBy(
                        tran -> YearMonth.from(tran.getTransactionDate())
                ));
    }

    private List<TransactionDto> asDtoList(List<Transaction> transactionList) {
        return transactionMapper.mapToTransactionDTOList(transactionList);
    }
}
