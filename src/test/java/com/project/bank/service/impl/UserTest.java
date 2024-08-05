package com.project.bank.service.impl;

import com.project.bank.model.entity.CheckingAccount;
import com.project.bank.model.entity.SavingsAccount;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.UserGenderEnum;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testUserGettersAndSetters() {
        User user = new User();
        user.setSsn("123456789");
        user.setFullName("John Doe");
        user.setIdCardNumber("ID123456");
        user.setEmail("john.doe@example.com");
        user.setUsername("johndoe");
        user.setPassword("password");
        user.setGender(UserGenderEnum.MALE);
        user.setPhoneNumber("1234567890");
        user.setClientNumber("CN123456");
        user.setRoles(new HashSet<>());
        CheckingAccount checkingAccount = new CheckingAccount();
        SavingsAccount savingsAccount = new SavingsAccount();
        user.setCheckingAccount(checkingAccount);
        user.setSavingsAccount(savingsAccount);

        assertEquals("123456789", user.getSsn());
        assertEquals("John Doe", user.getFullName());
        assertEquals("ID123456", user.getIdCardNumber());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("johndoe", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(UserGenderEnum.MALE, user.getGender());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals("CN123456", user.getClientNumber());
        assertNotNull(user.getRoles());
        assertEquals(checkingAccount, user.getCheckingAccount());
        assertEquals(savingsAccount, user.getSavingsAccount());
    }
}
