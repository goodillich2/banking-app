package com.example.bankingapp1.repository;

import com.example.bankingapp1.entity.other.Account;
import com.example.bankingapp1.entity.other.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserEmailAndStatus(String email, AccountStatus active);
}
