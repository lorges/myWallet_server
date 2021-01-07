package com.pl.restApi.controller;

import com.pl.restApi.RestApiApplication;
import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.model.enums.TransactionType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        classes = RestApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {

    private String TRANSACTIONS_URL;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void init() {
        this.TRANSACTIONS_URL = "http://localhost:" + this.port + "/transactions";
    }

    @Test
    @DisplayName("Integration test for fetching transaction and return empty list")
    public void shouldFetchAllTransaction_emptyList() {
        ResponseEntity<TransactionDto[]> responseEntity = this.restTemplate.getForEntity(this.TRANSACTIONS_URL, TransactionDto[].class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().length, 0);
    }

    @Test
    @DisplayName("Integration test for saving new transaction")
    public void shouldSaveNewTransaction_return200() {
        TransactionDto actualTransactionDto = new TransactionDto("0", "TestName", "", LocalDate.of(2020, 12, 12),
                BigDecimal.valueOf(1221.21), TransactionType.SPEND.name(), TransactionKind.FOOD.name());

        ResponseEntity<TransactionDto> responseEntity = this.restTemplate
                .postForEntity(this.TRANSACTIONS_URL, actualTransactionDto, TransactionDto.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertFalse(responseEntity.getBody().getId().equals(actualTransactionDto.getId()));
    }
}
