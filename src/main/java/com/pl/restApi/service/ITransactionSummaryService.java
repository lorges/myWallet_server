package com.pl.restApi.service;

import com.pl.restApi.dto.MonthTransactionSummaryDto;

import java.util.List;

public interface ITransactionSummaryService {

    List<MonthTransactionSummaryDto> summary();
}
