package com.project.bank.service.impl;

import com.project.bank.model.entity.SavingsAccount;
import com.project.bank.repository.SavingsAccountRepository;
import com.project.bank.service.SavingsAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SavingsAccountServiceImplTest {

    @Mock
    private SavingsAccountRepository savingsAccountRepository;

    @InjectMocks
    private SavingsAccountServiceImpl savingsAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        when(savingsAccountRepository.save(savingsAccount)).thenReturn(savingsAccount);

        SavingsAccount createdAccount = savingsAccountService.createSavingsAccount(savingsAccount);

        assertEquals(savingsAccount, createdAccount);
        verify(savingsAccountRepository, times(1)).save(savingsAccount);
    }

    @Test
    void testFindAllByUsername() {
        String username = "testUser";
        List<SavingsAccount> accounts = List.of(new SavingsAccount(), new SavingsAccount());
        when(savingsAccountRepository.findAllByUser_Username(username)).thenReturn(accounts);

        List<SavingsAccount> foundAccounts = savingsAccountService.findAllByUsername(username);

        assertEquals(accounts, foundAccounts);
        verify(savingsAccountRepository, times(1)).findAllByUser_Username(username);
    }

    @Test
    void testFindByClientNumber() {
        String clientNumber = "12345";
        SavingsAccount savingsAccount = new SavingsAccount();
        when(savingsAccountRepository.findByClientNumber(clientNumber)).thenReturn(savingsAccount);

        SavingsAccount foundAccount = savingsAccountService.findByClientNumber(clientNumber);

        assertEquals(savingsAccount, foundAccount);
        verify(savingsAccountRepository, times(1)).findByClientNumber(clientNumber);
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";
        SavingsAccount savingsAccount = new SavingsAccount();
        when(savingsAccountRepository.findByUsername(username)).thenReturn(Optional.of(savingsAccount));

        SavingsAccount foundAccount = savingsAccountService.findByUsername(username);

        assertEquals(savingsAccount, foundAccount);
        verify(savingsAccountRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindByUsername_NotFound() {
        String username = "testUser";
        when(savingsAccountRepository.findByUsername(username)).thenReturn(Optional.empty());

        SavingsAccount foundAccount = savingsAccountService.findByUsername(username);

        assertNull(foundAccount);
        verify(savingsAccountRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        SavingsAccount savingsAccount = new SavingsAccount();
        when(savingsAccountRepository.findById(id)).thenReturn(Optional.of(savingsAccount));

        SavingsAccount foundAccount = savingsAccountService.findById(id);

        assertEquals(savingsAccount, foundAccount);
        verify(savingsAccountRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(savingsAccountRepository.findById(id)).thenReturn(Optional.empty());

        SavingsAccount foundAccount = savingsAccountService.findById(id);

        assertNull(foundAccount);
        verify(savingsAccountRepository, times(1)).findById(id);
    }
}
