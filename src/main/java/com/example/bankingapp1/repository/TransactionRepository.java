package com.example.bankingapp1.repository;

import com.example.bankingapp1.entity.other.Account;
import com.example.bankingapp1.entity.other.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
   @Query("SELECT t FROM Transaction t WHERE t.fromAccount = :account OR t.toAccount = :account")
   List<Transaction> findByAccount(Account account);
}