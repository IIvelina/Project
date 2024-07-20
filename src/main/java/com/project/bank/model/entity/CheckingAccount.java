package com.project.bank.model.entity;

import jakarta.persistence.*;

import java.util.Random;

@Entity
@Table(name = "checking_accounts")
public class CheckingAccount extends Account {
    @Column(nullable = false, unique = true)
    private String debitCardNumber;

    @Column(nullable = false)
    private String debitCardPin;

    @OneToOne
    private User user;

    public CheckingAccount() {
        super();
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

    public void setDebitCardDetails() {
        this.debitCardNumber = generateRandomNumber(12);
        this.debitCardPin = generateRandomNumber(4);
    }

    private String generateRandomNumber(int digits) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
