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

    public final static Long TRANSACTION_ID_1 = 1L;
    public final static String TRANSACTION_NAME_1 = "Testowa";
    public final static String TRANSACTION_DESC_1 = "Testowy description";
    public final static BigDecimal TRANSACTION_AMOUNT_1 = BigDecimal.TEN;
    public final static LocalDate TRANSACTION_DATE_1 = LocalDate.of(2020,10,10);
    public final static TransactionKind TRANSACTION_KIND_1 = TransactionKind.FOOD;
    public final static TransactionType TRANSACTION_TYPE_1 = TransactionType.SPEND;

    public final static Long TRANSACTION_ID_2 = 2L;
    public final static String TRANSACTION_NAME_2 = "Testowa";
    public final static String TRANSACTION_DESC_2 = "Testowy description";
    public final static BigDecimal TRANSACTION_AMOUNT_2 = BigDecimal.ONE;
    public final static LocalDate TRANSACTION_DATE_2 = LocalDate.of(2020,10,14);
    public final static TransactionKind TRANSACTION_KIND_2 = TransactionKind.FOOD;
    public final static TransactionType TRANSACTION_TYPE_2 = TransactionType.SPEND;

    public final static Long TRANSACTION_ID_3 = 3L;
    public final static String TRANSACTION_NAME_3 = "Testowa 3 ";
    public final static String TRANSACTION_DESC_3 = "Testowy description 3";
    public final static BigDecimal TRANSACTION_AMOUNT_3 = BigDecimal.valueOf(1221.21);
    public final static LocalDate TRANSACTION_DATE_3 = LocalDate.of(2020,12,1);
    public final static TransactionKind TRANSACTION_KIND_3 = TransactionKind.FOOD;
    public final static TransactionType TRANSACTION_TYPE_3 = TransactionType.SPEND;

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
