package com.project.bank.model.entity;

import com.project.bank.model.enums.UserRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{

    @Column(name = "business_email", nullable = false, unique = true)
    private String businessEmail;
    @Column(nullable = false)
    private String password;

    @OneToOne
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private UserRoleEnum role;

    public Employee() {
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
