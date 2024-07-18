package com.project.bank.model.entity;

import com.project.bank.model.enums.UserRoleEnum;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private UserRoleEnum role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
        this.users = new HashSet<>();
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}