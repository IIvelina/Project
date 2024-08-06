package com.project.bank.web;

import com.project.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HomeControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIndexWithUserDetails() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");

        String viewName = homeController.index(userDetails, model);
        assertEquals("index", viewName);
        verify(model, times(1)).addAttribute("welcomeMessage", "Testuser");
    }

    @Test
    void testIndexWithoutUserDetails() {
        String viewName = homeController.index(null, model);
        assertEquals("index", viewName);
        verify(model, times(1)).addAttribute("welcomeMessage", "Anonymous");
    }

    @Test
    void testAboutUs() {
        String viewName = homeController.aboutUs();
        assertEquals("about", viewName);
    }

    @Test
    void testSecurityTips() {
        String viewName = homeController.securityTips();
        assertEquals("securityTips", viewName);
    }

    @Test
    void testTerms() {
        String viewName = homeController.terms();
        assertEquals("terms", viewName);
    }

    @Test
    void testForgotPassword() {
        String viewName = homeController.forgotPassword();
        assertEquals("forgotPassword", viewName);
    }

    @Test
    void testProductAndService() {
        String viewName = homeController.productAndService();
        assertEquals("service", viewName);
    }

    @Test
    void testApplicationProcess() {
        String viewName = homeController.applicationProcess();
        assertEquals("application-process", viewName);
    }

    @Test
    void testBenefits() {
        String viewName = homeController.benefits();
        assertEquals("benefits", viewName);
    }

    @Test
    void testOpenPositions() {
        String viewName = homeController.openPositions();
        assertEquals("openPositions", viewName);
    }

    @Test
    void testContact() {
        String viewName = homeController.contact();
        assertEquals("contact", viewName);
    }

    @Test
    void testCards() {
        String viewName = homeController.cards();
        assertEquals("cards", viewName);
    }

    @Test
    void testLoans() {
        String viewName = homeController.loans();
        assertEquals("loans", viewName);
    }

    @Test
    void testSavings() {
        String viewName = homeController.savings();
        assertEquals("savings", viewName);
    }
}