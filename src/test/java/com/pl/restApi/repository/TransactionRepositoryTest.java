package com.pl.restApi.repository;

import com.pl.restApi.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Test
    @Sql(scripts={"classpath:createTestTransaction.sql"})
    void whenInitializedTransactions_thenFindsAll() {
        List<Transaction> transactionList = transactionRepository.findAll();
        assertEquals(transactionList.size(), 1);
    }

    @Test
    void whenNoTransactionInRepository_returnEmptyList() {
        List<Transaction> transactionList = transactionRepository.findAll();
        assertEquals(transactionList.size(), 0);
    }

}
