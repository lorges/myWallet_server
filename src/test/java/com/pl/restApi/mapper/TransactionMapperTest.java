package com.pl.restApi.mapper;

import com.pl.restApi.BaseTransactionUtils;
import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.model.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class TransactionMapperTest extends BaseTransactionUtils {

    private TransactionMapper transactionMapper;

    @Autowired
    public TransactionMapperTest(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Test
    @DisplayName("Testing transaction mapper from DTO to entity objects")
    public void testMapper(){
        TransactionDto transactionDto = transactionDto();

        Transaction transaction = transactionMapper.toTransaction(transactionDto);
        assertEquals(transactionDto.getTransactionName(), transaction.getTransactionName());
        assertEquals(transactionDto.getTransactionAmount(), transaction.getTransactionAmount());
        assertEquals(transactionDto.getTransactionDesc(), transaction.getTransactionDesc());
        assertEquals(transactionDto.getTransactionDate(), transaction.getTransactionDate());
        assertEquals(transactionDto.getTransactionKind(), transaction.getTransactionKind().name());
        assertEquals(transactionDto.getTransactionType(), transaction.getTransactionType().name());
    }
}
