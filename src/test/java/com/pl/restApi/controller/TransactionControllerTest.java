package com.pl.restApi.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.restApi.BaseTransactionUtils;
import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.exception.TransactionNotFoundException;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.model.enums.TransactionType;
import com.pl.restApi.service.ITransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest extends BaseTransactionUtils {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITransactionService transactionService;

    public List<Transaction> transactionList;

    private final static String TRANSACTION_NAME = "Testowa";
    private final static String TRANSACTION_DESC = "Testowy description";
    private final static BigDecimal TRANSACTION_AMOUNT = BigDecimal.TEN;
    private final static LocalDate TRANSACTION_DATE = LocalDate.of(2020,10,10);
    private final static TransactionKind TRANSACTION_KIND = TransactionKind.FOOD;
    private final static TransactionType TRANSACTION_TYPE = TransactionType.SPEND;

    @BeforeEach
    void setUp() {
        this.transactionList = new ArrayList<>();
        transactionList.add(new Transaction(1L, "Testowa 1", "Testowe 1 desc",
                LocalDate.of(2020,10,10), BigDecimal.TEN, TransactionType.SPEND, TransactionKind.FOOD));

        transactionList.add(new Transaction(2L, "Testowa 2", "Testowe 2 desc",
                LocalDate.of(2020,10,10), BigDecimal.valueOf(23.333), TransactionType.SPEND, TransactionKind.FOOD));
    }

    @Test
    void shouldReturnEmptyListWhenTransactionsNotExists() throws Exception {
        given(transactionService.getTransactions()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/transactions/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnTransactionsWhenTransactionsExists() throws Exception {
        given(transactionService.getTransactions()).willReturn(transactionList);

        mockMvc.perform(get("/transactions/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].transactionName", equalTo("Testowa 1")))
                .andExpect(jsonPath("$[0].transactionDesc", equalTo("Testowe 1 desc")))
                .andExpect(jsonPath("$[0].transactionDate", is(LocalDate.of(2020,10, 10 ).toString())))
                .andExpect(jsonPath("$[0].transactionAmount", is(10)))
                .andExpect(jsonPath("$[0].transactionType", equalTo("SPEND")))
                .andExpect(jsonPath("$[0].transactionKind", equalTo("FOOD")))
                .andExpect(jsonPath("$[1].transactionName", equalTo("Testowa 2")))
                .andExpect(jsonPath("$[1].transactionDesc", equalTo("Testowe 2 desc")))
                .andExpect(jsonPath("$[1].transactionDate", is(LocalDate.of(2020,10, 10 ).toString())));

    }

    @Test
    void shouldReturnTransactionWhenSaved() throws Exception {
        Transaction transaction = transaction();

        given(transactionService.processSaveTransaction(transaction)).willReturn(transaction);

        mockMvc.perform(post("/transactions/")
                    .content(toJson(transactionDto()))
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("transactionName", equalTo(TRANSACTION_NAME)))
                .andExpect(jsonPath("transactionDesc", equalTo(TRANSACTION_DESC)))
                .andExpect(jsonPath("transactionAmount", is(10)))
                .andExpect(jsonPath("transactionType", equalTo(TRANSACTION_TYPE.name())))
                .andExpect(jsonPath("transactionDate", is(TRANSACTION_DATE.toString())))
                .andExpect(jsonPath("transactionKind", equalTo(TRANSACTION_KIND.name())));
    }

    @Test
    void shouldReturnTransactionWhenFind() throws Exception, TransactionNotFoundException {
        Transaction transaction = transaction();

        given(transactionService.getTransaction(1L)).willReturn(transaction);

        mockMvc.perform(get("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("transactionName", equalTo(TRANSACTION_NAME)))
                .andExpect(jsonPath("transactionDesc", equalTo(TRANSACTION_DESC)))
                .andExpect(jsonPath("transactionAmount", is(10)))
                .andExpect(jsonPath("transactionType", equalTo(TRANSACTION_TYPE.name())))
                .andExpect(jsonPath("transactionDate", is(TRANSACTION_DATE.toString())))
                .andExpect(jsonPath("transactionKind", equalTo(TRANSACTION_KIND.name())));
    }

    @Test
    void shouldReturnStatusOkWhenTransactionDeleted() throws Exception, TransactionNotFoundException {
        doNothing().when(transactionService).deleteTransaction(1L);

        mockMvc.perform(delete("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldThrowExceptionWhenTryDeletingNotExsitsTransaction() throws Exception, TransactionNotFoundException {
        doThrow(TransactionNotFoundException.class).when(transactionService).deleteTransaction(1L);

        mockMvc.perform(delete("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest());
    }

    //@Test
    void shouldReturnTransactionDtoWhenTransactionEdited() throws Exception, TransactionNotFoundException {
        TransactionDto transactionDto = transactionDto();

        //given(transactionService.editTransaction(1L, transactionDto)).willReturn(transactionDto);

        mockMvc.perform(put("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(transactionDto))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("transactionName", equalTo(TRANSACTION_NAME)))
                .andExpect(jsonPath("transactionDesc", equalTo(TRANSACTION_DESC)))
                .andExpect(jsonPath("transactionAmount", is(10)))
                .andExpect(jsonPath("transactionType", equalTo(TRANSACTION_TYPE.name())))
                .andExpect(jsonPath("transactionDate", is(TRANSACTION_DATE.toString())))
                .andExpect(jsonPath("transactionKind", equalTo(TRANSACTION_KIND.name())));
    }

    @Test
    void shouldThrowExceptionTransactionToEditNotFound() throws Exception, TransactionNotFoundException {
        TransactionDto transactionDto = transactionDto();

        doThrow(TransactionNotFoundException.class).when(transactionService).editTransaction(1L,transactionDto);

        mockMvc.perform(put("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(transactionDto))
                )
                .andExpect(status().isBadRequest());
    }

    public static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
