package com.project.bank.service;

import com.project.bank.model.entity.SavingsAccount;

import java.util.List;

public interface SavingsAccountService {
    SavingsAccount createSavingsAccount(SavingsAccount savingsAccount);
    List<SavingsAccount> findAllByUsername(String username);
    SavingsAccount findByClientNumber(String clientNumber); // Добавено
}
