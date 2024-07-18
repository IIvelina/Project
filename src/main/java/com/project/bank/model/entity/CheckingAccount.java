package com.project.bank.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "checking_accounts")
public class CheckingAccount extends Account {
    @Column(nullable = false, unique = true)
    private String debitCardNumber;

    @Column(nullable = false)
    private String debitCardPin;

    public CheckingAccount() {
    }

    public String getDebitCardNumber() {
        return debitCardNumber;
    }

    public void setDebitCardNumber(String debitCardNumber) {
        this.debitCardNumber = debitCardNumber;
    }

    public String getDebitCardPin() {
        return debitCardPin;
    }

    public void setDebitCardPin(String debitCardPin) {
        this.debitCardPin = debitCardPin;
    }
}
