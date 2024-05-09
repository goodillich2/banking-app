package com.example.bankingapp1.service;


import com.example.bankingapp1.CardNumberGenerator.CardNumberGenerator;
import com.example.bankingapp1.entity.other.Account;
import com.example.bankingapp1.entity.other.AccountStatus;
import com.example.bankingapp1.entity.other.Transaction;
import com.example.bankingapp1.entity.user.User;
import com.example.bankingapp1.repository.AccountRepository;
import com.example.bankingapp1.repository.TransactionRepository;
import com.example.bankingapp1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Account> findAllAccountsByEmail(String email) {
        return accountRepository.findAllByUserEmailAndStatus(email, AccountStatus.ACTIVE);
    }

    @Transactional
    public void createAccount(Account account, String email) {
        // Set additional properties if needed
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        account.setUser(user);
        account.setCardNumber(CardNumberGenerator.generateCardNumber());
        account.setCvv(CardNumberGenerator.generateCVV());
        account.setExpiryMonth(12); // Пример: использовать логику для генерации сроков действия
        account.setExpiryYear(2026); // Пример
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
    }


    @Transactional
    public void closeAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setStatus(AccountStatus.CLOSED);
        accountRepository.save(account);
    }

    @Transactional
    public void transferMoney(String fromCardNumber, String toCardNumber, BigDecimal amount) {
        Account fromAccount = accountRepository.findByCardNumber(fromCardNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account toAccount = accountRepository.findByCardNumber(toCardNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setTransactionDate(new Date());
        transaction.setDescription("Transfer from account " + fromCardNumber + " to " + toCardNumber);

        transactionRepository.save(transaction);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public Account findAccountById(long parseLong) {
        return accountRepository.findById(parseLong)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public Account findByCardNumber(String cardNumber) {
        return accountRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }


}

