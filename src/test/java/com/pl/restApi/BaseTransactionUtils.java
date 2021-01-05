package com.pl.restApi;

import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BaseTransactionUtils {

    protected final static String TRANSACTION_NAME_1 = "Testowa";
    protected final static String TRANSACTION_DESC_1 = "Testowy description";
    protected final static BigDecimal TRANSACTION_AMOUNT_1 = BigDecimal.TEN;
    protected final static LocalDate TRANSACTION_DATE_1 = LocalDate.of(2020,10,10);
    protected final static TransactionKind TRANSACTION_KIND_1 = TransactionKind.FOOD;
    protected final static TransactionType TRANSACTION_TYPE_1 = TransactionType.SPEND;

    protected final static String TRANSACTION_NAME_2 = "Testowa";
    protected final static String TRANSACTION_DESC_2 = "Testowy description";
    protected final static BigDecimal TRANSACTION_AMOUNT_2 = BigDecimal.ONE;
    protected final static LocalDate TRANSACTION_DATE_2 = LocalDate.of(2020,10,10);
    protected final static TransactionKind TRANSACTION_KIND_2 = TransactionKind.FOOD;
    protected final static TransactionType TRANSACTION_TYPE_2 = TransactionType.SPEND;

    protected Transaction transaction() {
        return Transaction.builder()
                .transactionName(TRANSACTION_NAME_1)
                .transactionDesc(TRANSACTION_DESC_1)
                .transactionAmount(TRANSACTION_AMOUNT_1)
                .transactionDate(TRANSACTION_DATE_1)
                .transactionKind(TRANSACTION_KIND_1)
                .transactionType(TRANSACTION_TYPE_1)
                .build();
    }

    protected TransactionDto transactionDto() {
        return TransactionDto.builder()
                .id("1L")
                .transactionName(TRANSACTION_NAME_1)
                .transactionDesc(TRANSACTION_DESC_1)
                .transactionAmount(TRANSACTION_AMOUNT_1)
                .transactionDate(TRANSACTION_DATE_1)
                .transactionKind(TRANSACTION_KIND_1.name())
                .transactionType(TRANSACTION_TYPE_1.name())
                .build();
    }

    protected List<TransactionDto> getTransactionDtoList() {
        List<TransactionDto> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(transactionDto());
        transactionDTOList.add(new TransactionDto("2L", BaseTransactionUtils.TRANSACTION_NAME_2, BaseTransactionUtils.TRANSACTION_DESC_2,
                BaseTransactionUtils.TRANSACTION_DATE_2, BaseTransactionUtils.TRANSACTION_AMOUNT_2, BaseTransactionUtils.TRANSACTION_TYPE_2.name(),
                BaseTransactionUtils.TRANSACTION_KIND_2.name()));
        return transactionDTOList;
    }
}
