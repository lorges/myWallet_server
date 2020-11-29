package com.pl.restApi.mapper;

import com.pl.restApi.BaseTransactionUtils;
import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransactionMapperTest extends BaseTransactionUtils {

    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    public void testMapper(){
        TransactionDto transactionDto = transactionDto();

        Transaction transaction = transactionMapper.toTransaction(transactionDto);
        assertEquals(transactionDto.getTransactionName(), transaction.getTransactionName());
    }
}
