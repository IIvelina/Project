package com.project.bank.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankUserDetailsServiceTest {

    public static final String TEST_USERNAME = "dilyan";

    private TestUserRepository testUserRepository;

    private BankUserDetailsService toTest;

    @BeforeEach
    void setUp() {
        testUserRepository = new TestUserRepository();
        toTest = new BankUserDetailsService(testUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        UserDetails userDetails = toTest.loadUserByUsername(TEST_USERNAME);

        assertNotNull(userDetails);
        assertEquals(TEST_USERNAME, userDetails.getUsername());
        assertEquals("topsecret", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {
        String nonExistingUsername = "nonexisting";
        assertThrows(UsernameNotFoundException.class, () -> toTest.loadUserByUsername(nonExistingUsername));
    }
}