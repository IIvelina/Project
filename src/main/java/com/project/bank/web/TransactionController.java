package com.project.bank.web;

import com.project.bank.model.entity.Account;
import com.project.bank.model.entity.Transaction;
import com.project.bank.model.enums.AccountType;
import com.project.bank.security.CurrentUser;
import com.project.bank.service.AccountService;
import com.project.bank.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TransactionController {
    //Отговаря за управлението на транзакциите.
    //
    //Създаване на нова транзакция
    //Виждане на детайли за транзакция

    //todo
    //Добавяне на пари в сметка
//    //Теглене на пари от сметка
//    //Прехвърляне на пари между сметки
//    //Виждане на последните транзакции



    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CurrentUser currentUser;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService, CurrentUser currentUser) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.currentUser = currentUser;
    }

    @GetMapping("/user/add-money")
    public String showAddMoneyForm(Model model, RedirectAttributes redirectAttributes) {
        boolean hasCheckingAccount = accountService.getAccountByTypeAndUserId(AccountType.CHECKING, currentUser.getId()) != null;
        boolean hasSavingsAccount = accountService.getAccountByTypeAndUserId(AccountType.SAVINGS, currentUser.getId()) != null;

        if (!hasCheckingAccount && !hasSavingsAccount) {
            redirectAttributes.addFlashAttribute("error", "You don't have any accounts. Please request a new product.");
            return "redirect:/user/request-new-product";
        }

        model.addAttribute("hasCheckingAccount", hasCheckingAccount);
        model.addAttribute("hasSavingsAccount", hasSavingsAccount);

        return "addMoney";
    }

    @PostMapping("/user/add-money")
    public String addMoney(@RequestParam("amount") BigDecimal amount, @RequestParam("accountType") String accountTypeStr, RedirectAttributes redirectAttributes) {
        AccountType accountType;
        try {
            accountType = AccountType.valueOf(accountTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid account type selected.");
            return "redirect:/user/add-money";
        }

        Account account = accountService.getAccountByTypeAndUserId(accountType, currentUser.getId());

        if (account == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid account type selected.");
            return "redirect:/user/request-new-product";
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setToAccount(account);
        transaction.setTimestamp(LocalDateTime.now());
        transactionService.saveTransaction(transaction);

        account.setBalance(account.getBalance().add(amount));
        accountService.saveAccount(account);

        return "redirect:/user/accounts";
    }

//    @GetMapping("/user/transactions")
//    public String showRecentTransactions(Model model) {
//        List<Transaction> transactions = transactionService.getRecentTransactions(currentUser.getId(), 3);
//        model.addAttribute("transactions", transactions);
//        model.addAttribute("currentUserName", currentUser.getFullName());
//        return "lastTransactionsEN";
//    }
}
