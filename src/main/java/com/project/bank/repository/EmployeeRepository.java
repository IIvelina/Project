package com.project.bank.repository;

import com.project.bank.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByBusinessEmail(String businessEmail);

    Optional<Employee> findByBusinessEmail(String businessEmail);

    Optional<Employee> findByBusinessEmailAndPassword(String businessEmail, String password);
}
