package com.pl.restApi.repository;

import com.pl.restApi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("transactionRepository")
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
}
