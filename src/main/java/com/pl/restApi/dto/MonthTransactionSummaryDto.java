package com.pl.restApi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Data
@NoArgsConstructor
public class MonthTransactionSummaryDto {

    private YearMonth monthDate;
    private BigDecimal incomeSummary;
    private BigDecimal spendSummary;
    private List<TransactionDto> transactionList;
}
