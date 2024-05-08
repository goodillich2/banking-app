package com.example.bankingapp1.service;



import com.example.bankingapp1.entity.other.Account;
import com.example.bankingapp1.entity.other.Transaction;
import com.example.bankingapp1.repository.AccountRepository;
import com.example.bankingapp1.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> findTransactionsByAccountId(Long accountId)
    {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
        return transactionRepository.findByAccount(account);
    }
}
