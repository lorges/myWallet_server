package com.pl.restApi;

import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BaseTransactionUtils {

    private final static String TRANSACTION_NAME = "Testowa";
    private final static String TRANSACTION_DESC = "Testowy description";
    private final static BigDecimal TRANSACTION_AMOUNT = BigDecimal.TEN;
    private final static LocalDate TRANSACTION_DATE = LocalDate.of(2020,10,10);
    private final static TransactionKind TRANSACTION_KIND = TransactionKind.FOOD;
    private final static TransactionType TRANSACTION_TYPE = TransactionType.SPEND;

    protected Transaction transaction() {
        return Transaction.builder()
                .transactionName(TRANSACTION_NAME)
                .transactionDesc(TRANSACTION_DESC)
                .transactionAmount(TRANSACTION_AMOUNT)
                .transactionDate(TRANSACTION_DATE)
                .transactionKind(TRANSACTION_KIND)
                .transactionType(TRANSACTION_TYPE)
                .build();
    }

    protected TransactionDto transactionDto() {
        return TransactionDto.builder()
                .transactionName(TRANSACTION_NAME)
                .transactionDesc(TRANSACTION_DESC)
                .transactionAmount(TRANSACTION_AMOUNT)
                .transactionDate(TRANSACTION_DATE)
                .transactionKind(TRANSACTION_KIND.name())
                .transactionType(TRANSACTION_TYPE.name())
                .build();
    }
}
