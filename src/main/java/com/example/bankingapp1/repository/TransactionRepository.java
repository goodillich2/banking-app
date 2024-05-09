package com.example.bankingapp1.repository;

import com.example.bankingapp1.entity.other.Account;
import com.example.bankingapp1.entity.other.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
   @Query("SELECT t FROM Transaction t WHERE t.fromAccount.accountId = :accountId OR t.toAccount.accountId = :accountId ORDER BY t.transactionDate DESC")
   List<Transaction> findByAccountIdSortedByDateDesc(Long accountId);
}