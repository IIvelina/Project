package com.project.bank.service.impl;

import com.project.bank.model.entity.Account;
import com.project.bank.model.enums.AccountType;
import com.project.bank.repository.AccountRepository;
import com.project.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccountByTypeAndUserId(AccountType type, Long userId) {
        Optional<Account> accountOpt = accountRepository.findByTypeAndUserId(type, userId);
        return accountOpt.orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Override
    public Account getAccountById(Long accountId) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        return accountOpt.orElse(null);
    }


}
