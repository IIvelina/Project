package com.project.bank.service.impl;

import com.project.bank.model.entity.Employee;
import com.project.bank.repository.EmployeeRepository;
import com.project.bank.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public boolean existsByBusinessEmail(String businessEmail) {
        return employeeRepository.existsByBusinessEmail(businessEmail);
    }
}
