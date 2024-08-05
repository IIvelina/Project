package com.project.bank.service.impl;

import com.project.bank.model.entity.Employee;
import com.project.bank.model.entity.User;
import com.project.bank.repository.EmployeeRepository;
import com.project.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEmployee() {
        Employee employee = new Employee();

        employeeServiceImpl.saveEmployee(employee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testExistsByBusinessEmail() {
        String businessEmail = "test@company.com";
        when(employeeRepository.existsByBusinessEmail(businessEmail)).thenReturn(true);

        boolean exists = employeeServiceImpl.existsByBusinessEmail(businessEmail);

        assertTrue(exists);
        verify(employeeRepository, times(1)).existsByBusinessEmail(businessEmail);
    }

    @Test
    void testFindByBusinessEmail() {
        String businessEmail = "test@company.com";
        Employee employee = new Employee();
        when(employeeRepository.findByBusinessEmail(businessEmail)).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = employeeServiceImpl.findByBusinessEmail(businessEmail);

        assertTrue(foundEmployee.isPresent());
        assertEquals(employee, foundEmployee.get());
        verify(employeeRepository, times(1)).findByBusinessEmail(businessEmail);
    }

    @Test
    void testAuthenticate() {
        String businessEmail = "test@company.com";
        String password = "password";
        Employee employee = new Employee();
        when(employeeRepository.findByBusinessEmailAndPassword(businessEmail, password)).thenReturn(Optional.of(employee));

        Employee authenticatedEmployee = employeeServiceImpl.authenticate(businessEmail, password);

        assertNotNull(authenticatedEmployee);
        assertEquals(employee, authenticatedEmployee);
        verify(employeeRepository, times(1)).findByBusinessEmailAndPassword(businessEmail, password);
    }

    @Test
    void testAuthenticateFail() {
        String businessEmail = "test@company.com";
        String password = "password";
        when(employeeRepository.findByBusinessEmailAndPassword(businessEmail, password)).thenReturn(Optional.empty());

        Employee authenticatedEmployee = employeeServiceImpl.authenticate(businessEmail, password);

        assertNull(authenticatedEmployee);
        verify(employeeRepository, times(1)).findByBusinessEmailAndPassword(businessEmail, password);
    }

    @Test
    void testGetClients() {
        User user1 = new User();
        User user2 = new User();
        List<User> clients = Arrays.asList(user1, user2);
        when(userService.getClients()).thenReturn(clients);

        List<User> result = employeeServiceImpl.getClients();

        assertEquals(clients, result);
        verify(userService, times(1)).getClients();
    }

    @Test
    void testIsEmployeeByEmail() {
        String username = "testuser";
        String businessEmail = username + "_wave@financial.com";
        when(employeeRepository.existsByBusinessEmail(businessEmail)).thenReturn(true);

        boolean isEmployee = employeeServiceImpl.isEmployeeByEmail(username);

        assertTrue(isEmployee);
        verify(employeeRepository, times(1)).existsByBusinessEmail(businessEmail);
    }
}