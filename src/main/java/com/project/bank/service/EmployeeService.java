package com.project.bank.service;

import com.project.bank.model.entity.Employee;

public interface EmployeeService {
    void saveEmployee(Employee employee);
    boolean existsByBusinessEmail(String businessEmail);
}
