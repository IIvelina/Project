package com.project.bank.web;

import com.project.bank.model.entity.*;
import com.project.bank.model.enums.AccountType;
import com.project.bank.service.AccountService;
import com.project.bank.service.TransactionService;
import com.project.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testShowAddMoneyForm_HasAccounts() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(accountService.getAccountByTypeAndUserId(AccountType.CHECKING, 1L)).thenReturn(new CheckingAccount());
        when(accountService.getAccountByTypeAndUserId(AccountType.SAVINGS, 1L)).thenReturn(new SavingsAccount());

        String viewName = transactionController.showAddMoneyForm(model, redirectAttributes);

        assertEquals("addMoney", viewName);
        verify(model, times(1)).addAttribute("hasCheckingAccount", true);
        verify(model, times(1)).addAttribute("hasSavingsAccount", true);
    }


    @Test
    void testShowAddMoneyForm_NoAccounts() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(accountService.getAccountByTypeAndUserId(AccountType.CHECKING, 1L)).thenReturn(null);
        when(accountService.getAccountByTypeAndUserId(AccountType.SAVINGS, 1L)).thenReturn(null);

        String viewName = transactionController.showAddMoneyForm(model, redirectAttributes);

        assertEquals("redirect:/user/request-new-product", viewName);
        verify(redirectAttributes, times(1)).addFlashAttribute("error", "You don't have any accounts. Please request a new product.");
    }

    @Test
    void testAddMoney_ValidAccount() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        // Използваме конкретен подклас на Account
        CheckingAccount account = new CheckingAccount();
        account.setBalance(BigDecimal.ZERO);
        account.setUser(user); // Свързваме акаунта с потребителя

        when(authentication.getName()).thenReturn("testUser");
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(accountService.getAccountByTypeAndUserId(AccountType.CHECKING, 1L)).thenReturn(account);

        String viewName = transactionController.addMoney(new BigDecimal("100"), "checking", redirectAttributes);

        assertEquals("redirect:/user/accounts", viewName);
        verify(transactionService, times(1)).saveTransaction(any(Transaction.class));
        verify(accountService, times(1)).saveAccount(account);
    }


    @Test
    void testAddMoney_InvalidAccountType() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getUserByUsername("testUser")).thenReturn(user);

        String viewName = transactionController.addMoney(new BigDecimal("100"), "invalid", redirectAttributes);

        assertEquals("redirect:/user/add-money", viewName);
        verify(redirectAttributes, times(1)).addFlashAttribute("error", "Invalid account type selected.");
    }

    @Test
    void testWithdraw_ValidAccount() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        // Използваме конкретен подклас на Account
        CheckingAccount account = new CheckingAccount();
        account.setBalance(new BigDecimal("100"));
        account.setUser(user); // Свързваме акаунта с потребителя

        when(authentication.getName()).thenReturn("testUser");
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(accountService.getAccountByTypeAndUserId(AccountType.CHECKING, 1L)).thenReturn(account);

        String viewName = transactionController.withdraw(new BigDecimal("50"), "checking", model, redirectAttributes);

        assertEquals("redirect:/user/accounts", viewName);
        verify(transactionService, times(1)).saveTransaction(any(Transaction.class));
        verify(accountService, times(1)).saveAccount(account);
    }


    @Test
    void testWithdraw_InsufficientBalance() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        // Използваме конкретен подклас на Account
        CheckingAccount account = new CheckingAccount();
        account.setBalance(new BigDecimal("30"));
        account.setUser(user);

        when(authentication.getName()).thenReturn("testUser");
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(accountService.getAccountByTypeAndUserId(AccountType.CHECKING, 1L)).thenReturn(account);

        String viewName = transactionController.withdraw(new BigDecimal("50"), "checking", model, redirectAttributes);

        assertEquals("withdraw", viewName);
        verify(model, times(1)).addAttribute("error", "Insufficient balance.");
    }


    @Test
    void testShowTransferForm_HasAccounts() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        // Създаваме списък с конкретни подкласове на Account
        List<Account> accounts = new ArrayList<>();
        CheckingAccount account = new CheckingAccount();
        account.setUser(user);
        accounts.add(account);

        when(authentication.getName()).thenReturn("testUser");
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(accountService.getAccountsByUserId(1L)).thenReturn(accounts);

        String viewName = transactionController.showTransferForm(model, redirectAttributes);

        assertEquals("transferEN", viewName);
        verify(model, times(1)).addAttribute("accounts", accounts);
    }


    @Test
    void testShowTransferForm_NoAccounts() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        List<Account> accounts = new ArrayList<>();
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(accountService.getAccountsByUserId(1L)).thenReturn(accounts);

        String viewName = transactionController.showTransferForm(model, redirectAttributes);

        assertEquals("redirect:/user/request-new-product", viewName);
        verify(redirectAttributes, times(1)).addFlashAttribute("error", "You don't have any accounts. Please request a new product.");
    }

    @Test
    void testTransfer_Valid() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        // Използваме конкретен подклас на Account
        CheckingAccount fromAccount = new CheckingAccount();
        fromAccount.setBalance(new BigDecimal("100"));
        fromAccount.setUser(user);

        User recipient = new User();
        recipient.setId(2L);

        // Използваме конкретен подклас на Account
        CheckingAccount recipientAccount = new CheckingAccount();
        recipientAccount.setUser(recipient);
        recipientAccount.setBalance(new BigDecimal("50"));

        when(authentication.getName()).thenReturn("testUser");
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(accountService.getAccountById(1L)).thenReturn(fromAccount);
        when(userService.getUserByPhoneNumber("12345")).thenReturn(recipient);
        when(accountService.getAccountByTypeAndUserId(AccountType.CHECKING, 2L)).thenReturn(recipientAccount);

        String viewName = transactionController.transfer(1L, "12345", new BigDecimal("50"), model, redirectAttributes);

        assertEquals("redirect:/user/accounts", viewName);
        verify(transactionService, times(1)).saveTransaction(any(Transaction.class));
        verify(accountService, times(1)).saveAccount(fromAccount);
        verify(accountService, times(1)).saveAccount(recipientAccount);
    }





    @Test
    void testFindRecentTransactions() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        List<Transaction> transactions = new ArrayList<>();
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(transactionService.getLastThreeTransactionsByUserId(1L)).thenReturn(transactions);

        String viewName = transactionController.findRecentTransactions(model);

        assertEquals("lastTransactionsEN", viewName);
        verify(model, times(1)).addAttribute("transactions", transactions);
        verify(model, times(1)).addAttribute("currentUserName", user.getFullName());
        verify(model, times(1)).addAttribute("currentUserId", 1L);
    }
}
