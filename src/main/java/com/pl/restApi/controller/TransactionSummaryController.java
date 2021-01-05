package com.pl.restApi.controller;

import com.pl.restApi.dto.MonthTransactionSummaryDto;
import com.pl.restApi.service.ITransactionSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping(path = "/transactionSummary")
@RequiredArgsConstructor
public class TransactionSummaryController {

    private final ITransactionSummaryService transactionSummaryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MonthTransactionSummaryDto> getTransactions() {
        log.info("TransactionSummaryController::getTransactions()");
        return transactionSummaryService.summary();
    }
}
