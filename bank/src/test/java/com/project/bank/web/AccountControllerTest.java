package com.project.bank.web;

import com.project.bank.model.entity.CheckingAccount;
import com.project.bank.model.entity.SavingsAccount;
import com.project.bank.model.entity.User;
import com.project.bank.service.CheckingAccountService;
import com.project.bank.service.ExRateService;
import com.project.bank.service.SavingsAccountService;
import com.project.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private SavingsAccountService savingsAccountService;

    @Mock
    private CheckingAccountService checkingAccountService;

    @Mock
    private UserService userService;

    @Mock
    private ExRateService exRateService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private Model model;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSavingsAccount() {
        User user = new User();
        user.setUsername("testUser");
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userService.findByUsername("testUser")).thenReturn(user);
        when(savingsAccountService.findAllByUsername("testUser")).thenReturn(new ArrayList<>());

        String result = accountController.createSavingsAccount(userDetails, "12345", new BigDecimal("1.5"), model);

        verify(savingsAccountService, times(1)).createSavingsAccount(any(SavingsAccount.class));
        verify(userService, times(1)).save(user);
        assertEquals("redirect:/user/accounts", result);
    }

    @Test
    void testCreateCheckingAccount() {
        User user = new User();
        user.setUsername("testUser");
        user.setClientNumber("12345");
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userService.findByUsername("testUser")).thenReturn(user);
        when(checkingAccountService.findAllByUsername("testUser")).thenReturn(new ArrayList<>());

        String result = accountController.createCheckingAccount(userDetails, "12345", model);

        verify(checkingAccountService, times(1)).createCheckingAccount(any(CheckingAccount.class));
        verify(userService, times(1)).save(user);
        assertEquals("redirect:/user/accounts", result);
    }

    @Test
    void testUserAccounts() {
        User user = new User();
        user.setUsername("testUser");
        when(userDetails.getUsername()).thenReturn("testUser");
        when(checkingAccountService.findAllByUsername("testUser")).thenReturn(new ArrayList<>());
        when(savingsAccountService.findAllByUsername("testUser")).thenReturn(new ArrayList<>());
        when(exRateService.allSupportedCurrencies()).thenReturn(new ArrayList<>());

        String result = accountController.userAccounts(userDetails, model);

        assertEquals("myAccountsEN", result);
    }

    @Test
    void testRequestNewProduct() {
        User user = new User();
        user.setUsername("testUser");
        user.setClientNumber("12345");
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userService.findByUsername("testUser")).thenReturn(user);
        when(checkingAccountService.findAllByUsername("testUser")).thenReturn(new ArrayList<>());
        when(savingsAccountService.findAllByUsername("testUser")).thenReturn(new ArrayList<>());

        String result = accountController.requestNewProduct(userDetails, model);

        assertEquals("newProductEN", result);
        verify(model, times(1)).addAttribute("clientNumber", "12345");
        verify(model, times(1)).addAttribute("hasCheckingAccount", false);
        verify(model, times(1)).addAttribute("hasSavingsAccount", false);
        verify(model, times(1)).addAttribute("isDisabled", false);
    }

    @Test
    void testGetCheckingInfo() {
        User user = new User();
        user.setUsername("testUser");
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setDebitCardNumber("123456789");
        checkingAccount.setDebitCardPin("1234");
        when(userDetails.getUsername()).thenReturn("testUser");
        when(checkingAccountService.findByUsername("testUser")).thenReturn(checkingAccount);

        String result = accountController.getCheckingInfo(userDetails, model);

        assertEquals("showCheckingInfo", result);
        verify(model, times(1)).addAttribute(eq("checkingAccount"), any());
        verify(model, never()).addAttribute(eq("error"), anyString());
    }

    @Test
    void testGetCheckingInfo_NoAccount() {
        when(userDetails.getUsername()).thenReturn("testUser");
        when(checkingAccountService.findByUsername("testUser")).thenReturn(null);

        String result = accountController.getCheckingInfo(userDetails, model);

        assertEquals("showCheckingInfo", result);
        verify(model, never()).addAttribute(eq("checkingAccount"), any());
        verify(model, times(1)).addAttribute("error", "No checking account found for the user.");
    }

    @Test
    void testGetSavingInfo() {
        User user = new User();
        user.setUsername("testUser");
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setSafetyDepositBox("Box123");
        savingsAccount.setSafetyDepositKey("Key123");
        when(userDetails.getUsername()).thenReturn("testUser");
        when(savingsAccountService.findByUsername("testUser")).thenReturn(savingsAccount);

        String result = accountController.getSavingInfo(userDetails, model);

        assertEquals("showSavingInfo", result);
        verify(model, times(1)).addAttribute(eq("savingsAccount"), any());
        verify(model, never()).addAttribute(eq("error"), anyString());
    }

    @Test
    void testGetSavingInfo_NoAccount() {
        when(userDetails.getUsername()).thenReturn("testUser");
        when(savingsAccountService.findByUsername("testUser")).thenReturn(null);

        String result = accountController.getSavingInfo(userDetails, model);

        assertEquals("showSavingInfo", result);
        verify(model, never()).addAttribute(eq("savingsAccount"), any());
        verify(model, times(1)).addAttribute("error", "No savings account found for the user.");
    }
}