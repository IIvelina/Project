package com.project.bank.service;

import com.project.bank.model.entity.Employee;

import java.util.Optional;

public interface EmployeeService {
    void saveEmployee(Employee employee);
    boolean existsByBusinessEmail(String businessEmail);

    Optional<Employee> findByBusinessEmail(String businessEmail);
}
