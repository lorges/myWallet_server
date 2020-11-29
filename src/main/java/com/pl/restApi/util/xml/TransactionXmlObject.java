package com.pl.restApi.util.xml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionXmlObject {

    private String transactionName;

    private String transactionDesc;

    private String transactionDate;

    private BigDecimal transactionAmount;

    private String transactionType;

    private String transactionKind;
}
