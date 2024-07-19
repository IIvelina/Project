package com.project.bank.web;

import com.project.bank.model.entity.Account;
import com.project.bank.model.entity.Transaction;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.AccountType;
import com.project.bank.security.CurrentUser;
import com.project.bank.service.AccountService;
import com.project.bank.service.TransactionService;

import com.project.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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

    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService, CurrentUser currentUser, UserService userService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.currentUser = currentUser;
        this.userService = userService;
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

//    @GetMapping("/user/transactions")
//    public String showRecentTransactions(Model model) {
//        return "lastTransactionsEN";
//    }

//todo show last three transactions
    @GetMapping("/user/transactions")
    public String findRecentTransactions(Model model) {
//        List<Transaction> transactions = transactionService.getLastThreeTransactionsByClientNumber(currentUser.getClientNumber());
//        model.addAttribute("transactions", transactions);
//        model.addAttribute("currentUserName", currentUser.getFullName());
        return "lastTransactionsEN";
    }

    //todo make transactions by client number
    @GetMapping("/user/transfer")
    public String transfer() {
        return "transferEN";
    }

////Теглене на пари от сметка

//    @GetMapping("/user/withdraw")
//    public String withdraw() {
//        return "withdraw";
//    }


    @GetMapping("/user/withdraw")
    public String showWithdrawForm(Model model, RedirectAttributes redirectAttributes) {
        boolean hasCheckingAccount = accountService.getAccountByTypeAndUserId(AccountType.CHECKING, currentUser.getId()) != null;
        boolean hasSavingsAccount = accountService.getAccountByTypeAndUserId(AccountType.SAVINGS, currentUser.getId()) != null;

        if (!hasCheckingAccount && !hasSavingsAccount) {
            redirectAttributes.addFlashAttribute("error", "You don't have any accounts. Please request a new product.");
            return "redirect:/user/request-new-product";
        }

        model.addAttribute("hasCheckingAccount", hasCheckingAccount);
        model.addAttribute("hasSavingsAccount", hasSavingsAccount);

        return "withdraw";
    }

    @PostMapping("/user/withdraw")
    public String withdraw(@RequestParam("amount") BigDecimal amount, @RequestParam("accountType") String accountTypeStr, Model model, RedirectAttributes redirectAttributes) {
        AccountType accountType;
        try {
            accountType = AccountType.valueOf(accountTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid account type selected.");
            return showWithdrawForm(model, redirectAttributes);
        }

        Account account = accountService.getAccountByTypeAndUserId(accountType, currentUser.getId());

        if (account == null) {
            model.addAttribute("error", "Invalid account type selected.");
            return showWithdrawForm(model, redirectAttributes);
        }

        if (account.getBalance().compareTo(amount) < 0) {
            model.addAttribute("error", "Insufficient balance.");
            return showWithdrawForm(model, redirectAttributes);
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(amount.negate()); // Negate the amount for withdrawal
        transaction.setFromAccount(account);
        transaction.setTimestamp(LocalDateTime.now());
        transactionService.saveTransaction(transaction);

        account.setBalance(account.getBalance().subtract(amount));
        accountService.saveAccount(account);

        return "redirect:/user/accounts";
    }

}
