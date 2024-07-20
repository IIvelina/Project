package com.project.bank.service.impl;

import com.project.bank.model.entity.SavingsAccount;
import com.project.bank.repository.SavingsAccountRepository;
import com.project.bank.service.SavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {

    private final SavingsAccountRepository savingsAccountRepository;

    @Autowired
    public SavingsAccountServiceImpl(SavingsAccountRepository savingsAccountRepository) {
        this.savingsAccountRepository = savingsAccountRepository;
    }

    @Override
    public SavingsAccount createSavingsAccount(SavingsAccount savingsAccount) {
        return savingsAccountRepository.save(savingsAccount);
    }

    @Override
    public List<SavingsAccount> findAllByUsername(String username) {
        return savingsAccountRepository.findAllByUser_Username(username);
    }

    @Override
    public SavingsAccount findByClientNumber(String clientNumber) {
        return savingsAccountRepository.findByClientNumber(clientNumber);
    }


    @Override
    public SavingsAccount findByUsername(String username) {
        return savingsAccountRepository.findByUsername(username).orElse(null);
    }


}
