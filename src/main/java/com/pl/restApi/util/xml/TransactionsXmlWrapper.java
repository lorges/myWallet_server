package com.pl.restApi.util.xml;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "transactions")
@XmlType(propOrder = { "transactionList"})
public class TransactionsXmlWrapper {

    @XmlElement(name = "transaction")
    private ArrayList<TransactionXmlObject> transactionList;

}
