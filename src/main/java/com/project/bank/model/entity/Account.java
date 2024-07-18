package com.project.bank.model.entity;

import com.project.bank.model.enums.AccountType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false, unique = true)
    private String clientNumber;

    @Column(nullable = false)
    private BigDecimal rate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Account() {
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
