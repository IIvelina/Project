package com.project.bank.web;

import com.project.bank.model.dto.EmployeeLoginDTO;
import com.project.bank.model.entity.CheckingAccount;
import com.project.bank.model.entity.Employee;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.UserRoleEnum;
import com.project.bank.service.EmployeeService;
import com.project.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoleControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private Model model;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginAsAdmin() {
        String viewName = roleController.loginAsAdmin();
        assertEquals("loginAsAdmin", viewName);
    }

    @Test
    void testLoginConfirm_ValidAdmin() {
        EmployeeLoginDTO loginDTO = new EmployeeLoginDTO();
        loginDTO.setBusinessEmail("admin@example.com");
        loginDTO.setPassword("password");

        Employee admin = new Employee();
        admin.setRole(UserRoleEnum.ADMIN);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(employeeService.authenticate("admin@example.com", "password")).thenReturn(admin);

        String viewName = roleController.loginConfirm(loginDTO, bindingResult, session, redirectAttributes, model);
        assertEquals("redirect:/user/admin/dashboard", viewName);
        verify(session, times(1)).setAttribute("loggedInUser", admin);
    }

    @Test
    void testLoginConfirm_InvalidCredentials() {
        EmployeeLoginDTO loginDTO = new EmployeeLoginDTO();
        loginDTO.setBusinessEmail("admin@example.com");
        loginDTO.setPassword("wrongpassword");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(employeeService.authenticate("admin@example.com", "wrongpassword")).thenReturn(null);

        String viewName = roleController.loginConfirm(loginDTO, bindingResult, session, redirectAttributes, model);
        assertEquals("loginAsAdmin", viewName);
        verify(model, times(1)).addAttribute("loginError", "Invalid email or password");
    }

    @Test
    void testAdminDashboard() {
        List<User> clients = new ArrayList<>();
        when(employeeService.getClients()).thenReturn(clients);

        String viewName = roleController.adminDashboard(model);
        assertEquals("admin-dashboard", viewName);
        verify(model, times(1)).addAttribute("clients", clients);
    }

    @Test
    void testDeleteClient_UserCanBeDeleted() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setCheckingAccount(null);
        user.setSavingsAccount(null);

        when(userService.findByUserById(1L)).thenReturn(Optional.of(user));
        when(employeeService.isEmployeeByEmail("testUser")).thenReturn(false);

        String viewName = roleController.deleteClient(1L, redirectAttributes);
        assertEquals("redirect:/user/admin/dashboard", viewName);
        verify(userService, times(1)).delete(user);
        verify(redirectAttributes, times(1)).addFlashAttribute("successMessage", "User deleted successfully.");
    }

    @Test
    void testDeleteClient_UserCannotBeDeleted() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        // User has a non-zero balance in checking account
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setBalance(new BigDecimal("100"));
        user.setCheckingAccount(checkingAccount);

        when(userService.findByUserById(1L)).thenReturn(Optional.of(user));

        String viewName = roleController.deleteClient(1L, redirectAttributes);
        assertEquals("redirect:/user/admin/dashboard", viewName);
        verify(userService, never()).delete(user);
        verify(redirectAttributes, times(1)).addFlashAttribute("errorMessage", "User cannot be deleted. Make sure they have zero balance in both accounts and are not an employee.");
    }

    @Test
    void testDeleteClient_UserNotFound() {
        when(userService.findByUserById(1L)).thenReturn(Optional.empty());

        String viewName = roleController.deleteClient(1L, redirectAttributes);
        assertEquals("redirect:/user/admin/dashboard", viewName);
        verify(userService, never()).delete(any(User.class));
        verify(redirectAttributes, times(1)).addFlashAttribute("errorMessage", "User not found.");
    }
}
