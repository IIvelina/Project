package com.project.bank.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{
    //todo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
