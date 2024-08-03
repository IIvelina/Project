package com.project.bank.model.entity;

import jakarta.persistence.*;
import java.util.Random;

@Entity
@Table(name = "savings_accounts")
public class SavingsAccount extends Account {
    @Column(nullable = false, unique = true)
    private String safetyDepositBox;

    @Column(nullable = false)
    private String safetyDepositKey;

    @OneToOne
    private User user;

    public SavingsAccount() {
        super();
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


    public void setSafetyDepositBoxAndKey() {
        this.safetyDepositBox = generateRandomNumber(3);
        this.safetyDepositKey = generateRandomNumber(4);
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
