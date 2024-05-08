package com.example.bankingapp1.controller;

import com.example.bankingapp1.dto.TransferDTO;

import com.example.bankingapp1.entity.other.Account;
import com.example.bankingapp1.entity.user.User;
import com.example.bankingapp1.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String listAccounts(Model model) {

        //получаем email юзера
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Account> accounts = accountService.findAllAccountsByEmail(userEmail);
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("account", new Account());
        return "account-form";
    }

    @PostMapping("/add")
    public String addAccount(@ModelAttribute Account account,  RedirectAttributes redirectAttributes) {

        //получаем email юзера
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        accountService.createAccount(account, userEmail);
        redirectAttributes.addFlashAttribute("successMessage", "Account successfully added!");
        return "redirect:/accounts";
    }

    @PostMapping("/delete/{accountId}")
    public String deleteAccount(@PathVariable Long accountId, RedirectAttributes redirectAttributes) {
        accountService.deleteAccount(accountId);
        redirectAttributes.addFlashAttribute("successMessage", "Account successfully closed!");
        return "redirect:/accounts";
    }

    @GetMapping("/transfer")
    public String showTransferForm(@RequestParam(required = false) Long fromAccountId, Model model) {
        TransferDTO transfer = new TransferDTO();
        if (fromAccountId != null) {
            transfer.setFromAccountId(fromAccountId);  // Устанавливаем исходный счет, если он передан
        }
        model.addAttribute("transfer", transfer);
        return "transfer-form";
    }

    @PostMapping("/transfer")
    public String performTransfer(@ModelAttribute TransferDTO transfer, RedirectAttributes redirectAttributes) {
        try {
            System.out.println(transfer.getFromAccountId());
            System.out.println( transfer.getToAccountId());

            accountService.transferMoney(transfer.getFromAccountId(), transfer.getToAccountId(), transfer.getAmount());
            redirectAttributes.addFlashAttribute("successMessage", "Transfer successful!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error during transfer: " + e.getMessage());
        }
        return "redirect:/accounts";
    }


//    @GetMapping("/transfer")
//    public String showFindAccountForm(@RequestParam(required = false) Long fromAccountId, Model model) {
//        model.addAttribute("transfer", new TransferDTO());
//
//        return "find-account-form";
//    }
//
//    @PostMapping("/find-account")
//    public String findAccount(@RequestParam String accountId, Model model, RedirectAttributes redirectAttributes) {
//        try {
//            Account recipientAccount = accountService.findAccountById(Long.parseLong(accountId));
//            model.addAttribute("recipientAccount", recipientAccount);
//            TransferDTO transfer = new TransferDTO();
//            transfer.setToAccountId(recipientAccount.getAccountId());
//            model.addAttribute("transfer", transfer);
//            return "confirm-transfer";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Account not found.");
//            return "redirect:/accounts/transfer";
//        }
//    }


}



