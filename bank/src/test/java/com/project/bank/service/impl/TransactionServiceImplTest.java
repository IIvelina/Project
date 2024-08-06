package com.project.bank.service.impl;
import com.project.bank.model.entity.Transaction;
import com.project.bank.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTransaction() {
        Transaction transaction = new Transaction();

        transactionServiceImpl.saveTransaction(transaction);

        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testGetLastThreeTransactionsByUserId() {
        Long userId = 1L;
        Transaction transaction1 = new Transaction();
        transaction1.setTimestamp(LocalDateTime.now().minusDays(1));
        Transaction transaction2 = new Transaction();
        transaction2.setTimestamp(LocalDateTime.now().minusDays(2));
        Transaction transaction3 = new Transaction();
        transaction3.setTimestamp(LocalDateTime.now().minusDays(3));

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        when(transactionRepository.findTop3ByToAccount_UserIdOrFromAccount_UserIdOrderByTimestampDesc(userId, userId))
                .thenReturn(transactions);

        List<Transaction> result = transactionServiceImpl.getLastThreeTransactionsByUserId(userId);

        assertEquals(3, result.size());
        assertEquals(transaction3, result.get(0));
        assertEquals(transaction2, result.get(1));
        assertEquals(transaction1, result.get(2));

        verify(transactionRepository, times(1)).findTop3ByToAccount_UserIdOrFromAccount_UserIdOrderByTimestampDesc(userId, userId);
    }
}