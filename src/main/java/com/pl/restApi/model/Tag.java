package com.pl.restApi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tagName;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Transaction> tagTransactions;
}
