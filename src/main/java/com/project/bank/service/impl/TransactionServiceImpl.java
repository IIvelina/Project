package com.project.bank.service.impl;

import com.project.bank.model.entity.Transaction;
import com.project.bank.repository.TransactionRepository;
import com.project.bank.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }


//    @Override
//    public List<Transaction> getLastThreeTransactionsByUserId(Long userId) {
//        List<Transaction> transactions = transactionRepository.findTop3ByFromAccount_UserIdOrderByTimestampDesc(userId);
//        transactions.sort(Comparator.comparing(Transaction::getTimestamp));
//        return transactions;
//    }


    @Override
    public List<Transaction> getLastThreeTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findTop3ByToAccount_UserIdOrFromAccount_UserIdOrderByTimestampDesc(userId, userId);
        transactions.sort(Comparator.comparing(Transaction::getTimestamp));
        return transactions;
    }



}
