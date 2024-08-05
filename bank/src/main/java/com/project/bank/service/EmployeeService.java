package com.project.bank.service;

import com.project.bank.model.entity.Employee;
import com.project.bank.model.entity.User;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    void saveEmployee(Employee employee);
    boolean existsByBusinessEmail(String businessEmail);

    Optional<Employee> findByBusinessEmail(String businessEmail);

    Employee authenticate(String businessEmail, String password);

    List<User> getClients();

    boolean isEmployeeByEmail(String email);
}
