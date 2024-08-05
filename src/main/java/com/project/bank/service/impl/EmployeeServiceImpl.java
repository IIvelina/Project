package com.project.bank.service.impl;

import com.project.bank.model.entity.Employee;
import com.project.bank.model.entity.User;
import com.project.bank.repository.EmployeeRepository;
import com.project.bank.service.EmployeeService;
import com.project.bank.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final UserService userService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public boolean existsByBusinessEmail(String businessEmail) {
        return employeeRepository.existsByBusinessEmail(businessEmail);
    }

    @Override
    public Optional<Employee> findByBusinessEmail(String businessEmail) {
        return employeeRepository.findByBusinessEmail(businessEmail);
    }

    @Override
    public Employee authenticate(String businessEmail, String password) {
        return employeeRepository.findByBusinessEmailAndPassword(businessEmail, password).orElse(null);
    }

    @Override
    public List<User> getClients() {
        return userService.getClients();
    }

    @Override
    public boolean isEmployeeByEmail(String username) {
        String businessEmail = username + "_wave@financial.com";
        return employeeRepository.existsByBusinessEmail(businessEmail);
    }
}
