package com.project.bank.web;

import com.project.bank.model.dto.MyAccountDetailsDTO;
import com.project.bank.model.entity.Account;
import com.project.bank.model.entity.CheckingAccount;
import com.project.bank.model.entity.SavingsAccount;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.AccountType;
import com.project.bank.repository.AccountRepository;
import com.project.bank.service.ExRateService;
import com.project.bank.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ExRateService exRateService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccountByTypeAndUserId() {
        CheckingAccount account = new CheckingAccount();
        account.setType(AccountType.CHECKING);
        account.setUser(new User());
        account.getUser().setId(1L);

        when(accountRepository.findByTypeAndUserId(AccountType.CHECKING, 1L)).thenReturn(Optional.of(account));

        Account result = accountService.getAccountByTypeAndUserId(AccountType.CHECKING, 1L);
        assertNotNull(result);
        assertEquals(AccountType.CHECKING, result.getType());
        assertEquals(1L, result.getUser().getId());

        verify(accountRepository, times(1)).findByTypeAndUserId(AccountType.CHECKING, 1L);
    }

    @Test
    void testSaveAccount() {
        Account account = new CheckingAccount();
        accountService.saveAccount(account);

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testGetAccountsByUserId() {
        Account account1 = new CheckingAccount();
        Account account2 = new SavingsAccount();

        when(accountRepository.findByUserId(1L)).thenReturn(List.of(account1, account2));

        List<Account> accounts = accountService.getAccountsByUserId(1L);
        assertNotNull(accounts);
        assertEquals(2, accounts.size());

        verify(accountRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetAccountById() {
        CheckingAccount account = new CheckingAccount();
        account.setId(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.getAccountById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(accountRepository, times(1)).findById(1L);
    }


}