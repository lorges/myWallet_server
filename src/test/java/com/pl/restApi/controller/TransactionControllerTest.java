package com.pl.restApi.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.restApi.BaseTransactionUtils;
import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.exception.TransactionNotFoundException;
import com.pl.restApi.mapper.TransactionMapper;
import com.pl.restApi.model.Transaction;
import com.pl.restApi.service.ITransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
    @MockBean
    private TransactionMapper transactionMapper;

    public List<Transaction> transactionList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        this.transactionList = new ArrayList<>();
        transactionList.add(transaction());
        transactionList.add(new Transaction(2L, BaseTransactionUtils.TRANSACTION_NAME_2, BaseTransactionUtils.TRANSACTION_DESC_2,
                BaseTransactionUtils.TRANSACTION_DATE_2, BaseTransactionUtils.TRANSACTION_AMOUNT_2, BaseTransactionUtils.TRANSACTION_TYPE_2,
                BaseTransactionUtils.TRANSACTION_KIND_2, new HashSet<>()
        ));
    }

    @Test
    void getTransaction_shouldReturnTransaction_whenExists() throws Exception {
        given(transactionService.getTransaction(1L)).willReturn(transaction());
        given(transactionMapper.toTransactionDto(transaction())).willReturn(transactionDto());

        mockMvc.perform(get("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("transactionName", equalTo(TRANSACTION_NAME_1)))
                .andExpect(jsonPath("transactionDesc", equalTo(TRANSACTION_DESC_1)))
                .andExpect(jsonPath("transactionAmount", is(10)))
                .andExpect(jsonPath("transactionKind", equalTo(TRANSACTION_KIND_1.name())))
                .andExpect(jsonPath("transactionType", equalTo(TRANSACTION_TYPE_1.name())))
                .andExpect(jsonPath("transactionDate", is(TRANSACTION_DATE_1.toString())));
    }

    @Test
    void getTransaction_shouldThrowTransactionNotFoundException_whenNotExists() throws Exception {
        doThrow(TransactionNotFoundException.class).when(transactionService).getTransaction(1L);

        mockMvc.perform(get("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTransactions_shouldReturnEmptyList_whenTransactionsNotExists() throws Exception {
        given(transactionService.getTransactions()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/transactions/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getTransaction_shouldReturnTransactions_whenTransactionsExists() throws Exception {
        given(transactionService.getTransactions()).willReturn(transactionList);
        given(transactionMapper.mapToTransactionDTOList(transactionList)).willReturn(getTransactionDtoList());

        mockMvc.perform(get("/transactions/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].transactionName", equalTo(TRANSACTION_NAME_1)))
                .andExpect(jsonPath("$[0].transactionDate", is(TRANSACTION_DATE_1.toString())))
                .andExpect(jsonPath("$[0].transactionAmount", is(10)))
                .andExpect(jsonPath("$[0].transactionType", equalTo(TRANSACTION_TYPE_1.name())))
                .andExpect(jsonPath("$[0].transactionKind", equalTo(TRANSACTION_KIND_1.name())))

                .andExpect(jsonPath("$[1].transactionName", equalTo(TRANSACTION_NAME_2)))
                .andExpect(jsonPath("$[1].transactionDate", is(TRANSACTION_DATE_2.toString())))
                .andExpect(jsonPath("$[1].transactionAmount", is(1)))
                .andExpect(jsonPath("$[1].transactionType", equalTo(TRANSACTION_TYPE_2.name())))
                .andExpect(jsonPath("$[1].transactionKind", equalTo(TRANSACTION_KIND_2.name())));
    }

    @Test
    void saveTransaction_shouldReturnTransaction_whenSaved() throws Exception {
        given(transactionService.processSaveTransaction(transactionDto())).willReturn(transaction());
        given(transactionMapper.toTransactionDto(transaction())).willReturn(transactionDto());

        mockMvc.perform(post("/transactions/")
                    .content(toJson(transactionDto()))
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("transactionName", equalTo(TRANSACTION_NAME_1)))
                .andExpect(jsonPath("transactionDesc", equalTo(TRANSACTION_DESC_1)))
                .andExpect(jsonPath("transactionAmount", is(10)))
                .andExpect(jsonPath("transactionType", equalTo(TRANSACTION_TYPE_1.name())))
                .andExpect(jsonPath("transactionDate", is(TRANSACTION_DATE_1.toString())))
                .andExpect(jsonPath("transactionKind", equalTo(TRANSACTION_KIND_1.name())));
    }

    @Test
    void deleteTransaction_shouldReturnStatusNoContent_whenTransactionDeleted() throws Exception, TransactionNotFoundException {
        doNothing().when(transactionService).deleteTransaction(1L);

        mockMvc.perform(delete("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void  deleteTransaction_shouldThrowException_whenTryDeletingNotExistsTransaction() throws Exception, TransactionNotFoundException {
        doThrow(TransactionNotFoundException.class).when(transactionService).deleteTransaction(1L);

        mockMvc.perform(delete("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void editTransaction_shouldReturnTransactionDto_whenTransactionEdited() throws Exception, TransactionNotFoundException {
        TransactionDto transactionDto = transactionDto();
        given(transactionService.editTransaction(1L, transactionDto)).willReturn(transaction());
        given(transactionMapper.toTransactionDto(transaction())).willReturn(transactionDto());

        mockMvc.perform(put("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(transactionDto()))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id", equalTo("1L")))
                .andExpect(jsonPath("transactionName", equalTo(TRANSACTION_NAME_1)))
                .andExpect(jsonPath("transactionDesc", equalTo(TRANSACTION_DESC_1)))
                .andExpect(jsonPath("transactionAmount", is(10)))
                .andExpect(jsonPath("transactionType", equalTo(TRANSACTION_TYPE_1.name())))
                .andExpect(jsonPath("transactionDate", is(TRANSACTION_DATE_1.toString())))
                .andExpect(jsonPath("transactionKind", equalTo(TRANSACTION_KIND_1.name())));
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
