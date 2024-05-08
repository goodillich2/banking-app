package com.example.bankingapp1.controller;


import com.example.bankingapp1.entity.other.Transaction;
import com.example.bankingapp1.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{accountId}")
    public String viewTransactionsByAccount(@PathVariable Long accountId, Model model) {
        List<Transaction> transactions = transactionService.findTransactionsByAccountId(accountId);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
}
