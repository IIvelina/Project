package com.project.bank.repository;

import com.project.bank.model.entity.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {
    List<SavingsAccount> findAllByUserUsername(String username);

    List<SavingsAccount> findAllByUser_Username(String username);

    SavingsAccount findByClientNumber(String clientNumber);
}
