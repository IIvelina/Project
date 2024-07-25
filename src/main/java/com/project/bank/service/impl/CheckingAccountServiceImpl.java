package com.project.bank.service.impl;

import com.project.bank.model.entity.CheckingAccount;
import com.project.bank.repository.CheckingAccountRepository;
import com.project.bank.service.CheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckingAccountServiceImpl implements CheckingAccountService {

    private final CheckingAccountRepository checkingAccountRepository;

    @Autowired
    public CheckingAccountServiceImpl(CheckingAccountRepository checkingAccountRepository) {
        this.checkingAccountRepository = checkingAccountRepository;
    }

    @Override
    public CheckingAccount createCheckingAccount(CheckingAccount checkingAccount) {
        return checkingAccountRepository.save(checkingAccount);
    }

    @Override
    public List<CheckingAccount> findAllByUsername(String username) {
        return checkingAccountRepository.findAllByUser_Username(username);
    }



    @Override
    public CheckingAccount findByUsername(String username) {
        return checkingAccountRepository.findByUsername(username).orElse(null);
    }


}
