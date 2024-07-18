package com.project.bank.service;

import com.project.bank.model.entity.CheckingAccount;

import java.util.List;

public interface CheckingAccountService {
    CheckingAccount createCheckingAccount(CheckingAccount checkingAccount);
    List<CheckingAccount> findAllByUsername(String username);
    CheckingAccount findByClientNumber(String clientNumber); // Добавено

}
