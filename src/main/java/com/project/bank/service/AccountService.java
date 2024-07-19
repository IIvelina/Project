package com.project.bank.service;

import com.project.bank.model.entity.Account;
import com.project.bank.model.enums.AccountType;

import java.util.List;

public interface AccountService {
    Account getAccountByTypeAndUserId(AccountType type, Long userId);
    void saveAccount(Account account);

}
