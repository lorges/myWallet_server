package com.pl.restApi.model;

import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.model.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String transactionName;
    private String transactionDesc;
    private LocalDate transactionDate;
    private BigDecimal transactionAmount;
    private TransactionType transactionType;
    private TransactionKind transactionKind;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "transaction_tags",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
}
