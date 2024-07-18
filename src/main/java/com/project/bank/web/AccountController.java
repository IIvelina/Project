package com.project.bank.web;

import com.project.bank.model.entity.CheckingAccount;
import com.project.bank.model.entity.SavingsAccount;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.AccountType;
import com.project.bank.security.CurrentUser;
import com.project.bank.service.CheckingAccountService;
import com.project.bank.service.SavingsAccountService;
import com.project.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import java.util.List;

@Controller
public class AccountController {
    private final SavingsAccountService savingsAccountService;
    private final CheckingAccountService checkingAccountService;
    private final UserService userService;
    private final CurrentUser currentUser;

    @Autowired
    public AccountController(SavingsAccountService savingsAccountService,
                             CheckingAccountService checkingAccountService,
                             UserService userService,
                             CurrentUser currentUser) {
        this.savingsAccountService = savingsAccountService;
        this.checkingAccountService = checkingAccountService;
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @PostMapping("/user/create-savings-account")
    public String createSavingsAccount(@RequestParam("clientNumber") String clientNumber,
                                       @RequestParam("rate") BigDecimal rate,
                                       @RequestParam("safetyDepositBox") String safetyDepositBox,
                                       @RequestParam("safetyDepositKey") String safetyDepositKey,
                                       Model model) {
        User user = userService.findByUsername(currentUser.getUsername());
        SavingsAccount existingSavingsAccount = savingsAccountService.findByClientNumber(clientNumber);

        if (existingSavingsAccount != null) {
            model.addAttribute("error", "You already have a savings account.");
            return "newProduct";
        }

        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setClientNumber(clientNumber);
        savingsAccount.setRate(rate);
        savingsAccount.setSafetyDepositBox(safetyDepositBox);
        savingsAccount.setSafetyDepositKey(safetyDepositKey);
        savingsAccount.setBalance(BigDecimal.ZERO);
        savingsAccount.setType(AccountType.SAVINGS);
        savingsAccount.setUser(user);

        savingsAccountService.createSavingsAccount(savingsAccount);

        return "redirect:/user/accounts";
    }

    @PostMapping("/user/create-checking-account")
    public String createCheckingAccount(@RequestParam("clientNumber") String clientNumber,
                                        @RequestParam("debitCardNumber") String debitCardNumber,
                                        @RequestParam("debitCardPin") String debitCardPin,
                                        Model model) {
        User user = userService.findByUsername(currentUser.getUsername());
        CheckingAccount existingCheckingAccount = checkingAccountService.findByClientNumber(clientNumber);

        if (existingCheckingAccount != null) {
            model.addAttribute("error", "You already have a checking account.");
            return "newProduct";
        }

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setClientNumber(clientNumber);
        checkingAccount.setDebitCardNumber(debitCardNumber);
        checkingAccount.setDebitCardPin(debitCardPin);
        checkingAccount.setBalance(BigDecimal.ZERO);
        checkingAccount.setType(AccountType.CHECKING);
        checkingAccount.setUser(user);

        checkingAccountService.createCheckingAccount(checkingAccount);

        return "redirect:/user/accounts";
    }

    @GetMapping("/user/accounts")
    public String userAccounts(Model model) {
        String username = currentUser.getUsername();

        List<CheckingAccount> checkingAccounts = checkingAccountService.findAllByUsername(username);
        List<SavingsAccount> savingsAccounts = savingsAccountService.findAllByUsername(username);

        model.addAttribute("checkingAccounts", checkingAccounts);
        model.addAttribute("savingsAccounts", savingsAccounts);

        return "myAccountsEN";
    }

    @GetMapping("/user/transactions")
    public String recentTransactions() {
        return "lastTransactionsEN";
    }

    @GetMapping("/user/request-new-product")
    public String requestNewProduct(Model model) {
        User user = userService.findByUsername(currentUser.getUsername());
        List<CheckingAccount> checkingAccounts = checkingAccountService.findAllByUsername(user.getUsername());
        List<SavingsAccount> savingsAccounts = savingsAccountService.findAllByUsername(user.getUsername());

        boolean hasCheckingAccount = !checkingAccounts.isEmpty();
        boolean hasSavingsAccount = !savingsAccounts.isEmpty();

        model.addAttribute("clientNumber", user.getClientNumber());
        model.addAttribute("hasCheckingAccount", hasCheckingAccount);
        model.addAttribute("hasSavingsAccount", hasSavingsAccount);
        model.addAttribute("isDisabled", hasCheckingAccount && hasSavingsAccount);

        return "newProductEN";
    }

    @GetMapping("/user/add-money")
    public String addMoney() {
        return "addMoney";
    }

    @GetMapping("/user/withdraw")
    public String withdraw() {
        return "withdraw";
    }

    @GetMapping("/user/transfer")
    public String transfer() {
        return "transferEN";
    }


}




//
////    @GetMapping("/user/accounts")
////    public String userAccounts(){
////        return "myAccountsEN";
////    }
//
//    @GetMapping("/user/transactions")
//    public String recentTransactions(){
//        return "lastTransactionsEN";
//    }
//
//    @GetMapping("/user/request-new-product")
//    public String requestNewProduct(){
//        return "newProductEN";
//    }
//
//    @GetMapping("/user/add-money")
//    public String addMoney(){
//        return "addMoney";
//    }
//
//    @GetMapping("/user/withdraw")
//    public String withdraw(){
//        return "withdraw";
//    }
//
//    @GetMapping("/user/transfer")
//    public String transfer(){
//        return "transferEN";
//    }
//    //Отговаря за управлението на сметките.
//    //
//    //Създаване на нови сметки (спестовни или разплащателни)
//    //Добавяне на пари в сметка
//    //Теглене на пари от сметка
//    //Прехвърляне на пари между сметки
//    //Виждане на последните транзакции


