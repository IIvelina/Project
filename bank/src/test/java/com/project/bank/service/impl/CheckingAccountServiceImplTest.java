package com.project.bank.service.impl;

import com.project.bank.model.entity.CheckingAccount;
import com.project.bank.repository.CheckingAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckingAccountServiceImplTest {

    @Mock
    private CheckingAccountRepository checkingAccountRepository;

    @InjectMocks
    private CheckingAccountServiceImpl checkingAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCheckingAccount() {
        CheckingAccount checkingAccount = new CheckingAccount();
        when(checkingAccountRepository.save(checkingAccount)).thenReturn(checkingAccount);

        CheckingAccount createdAccount = checkingAccountService.createCheckingAccount(checkingAccount);

        assertEquals(checkingAccount, createdAccount);
        verify(checkingAccountRepository, times(1)).save(checkingAccount);
    }

    @Test
    void testFindAllByUsername() {
        String username = "testUser";
        List<CheckingAccount> accounts = List.of(new CheckingAccount(), new CheckingAccount());
        when(checkingAccountRepository.findAllByUser_Username(username)).thenReturn(accounts);

        List<CheckingAccount> foundAccounts = checkingAccountService.findAllByUsername(username);

        assertEquals(accounts, foundAccounts);
        verify(checkingAccountRepository, times(1)).findAllByUser_Username(username);
    }

    @Test
    void testFindByClientNumber() {
        String clientNumber = "12345";
        CheckingAccount checkingAccount = new CheckingAccount();
        when(checkingAccountRepository.findByClientNumber(clientNumber)).thenReturn(checkingAccount);

        CheckingAccount foundAccount = checkingAccountService.findByClientNumber(clientNumber);

        assertEquals(checkingAccount, foundAccount);
        verify(checkingAccountRepository, times(1)).findByClientNumber(clientNumber);
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";
        CheckingAccount checkingAccount = new CheckingAccount();
        when(checkingAccountRepository.findByUsername(username)).thenReturn(Optional.of(checkingAccount));

        CheckingAccount foundAccount = checkingAccountService.findByUsername(username);

        assertEquals(checkingAccount, foundAccount);
        verify(checkingAccountRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindByUsername_NotFound() {
        String username = "testUser";
        when(checkingAccountRepository.findByUsername(username)).thenReturn(Optional.empty());

        CheckingAccount foundAccount = checkingAccountService.findByUsername(username);

        assertNull(foundAccount);
        verify(checkingAccountRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        CheckingAccount checkingAccount = new CheckingAccount();
        when(checkingAccountRepository.findById(id)).thenReturn(Optional.of(checkingAccount));

        CheckingAccount foundAccount = checkingAccountService.findById(id);

        assertEquals(checkingAccount, foundAccount);
        verify(checkingAccountRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(checkingAccountRepository.findById(id)).thenReturn(Optional.empty());

        CheckingAccount foundAccount = checkingAccountService.findById(id);

        assertNull(foundAccount);
        verify(checkingAccountRepository, times(1)).findById(id);
    }
}