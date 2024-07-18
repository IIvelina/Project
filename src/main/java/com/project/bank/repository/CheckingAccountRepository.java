package com.project.bank.repository;

import com.project.bank.model.entity.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
    List<CheckingAccount> findAllByUserUsername(String username);

    List<CheckingAccount> findAllByUser_Username(String username);

    CheckingAccount findByClientNumber(String clientNumber);
}
