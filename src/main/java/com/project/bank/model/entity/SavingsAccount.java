package com.project.bank.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "savings_accounts")
public class SavingsAccount extends Account {
    @Column(nullable = false, unique = true)
    private String safetyDepositBox;

    @Column(nullable = false)
    private String safetyDepositKey;

    public SavingsAccount() {
    }

    public String getSafetyDepositBox() {
        return safetyDepositBox;
    }

    public void setSafetyDepositBox(String safetyDepositBox) {
        this.safetyDepositBox = safetyDepositBox;
    }

    public String getSafetyDepositKey() {
        return safetyDepositKey;
    }

    public void setSafetyDepositKey(String safetyDepositKey) {
        this.safetyDepositKey = safetyDepositKey;
    }
}
