package com.project.bank.repository;

import com.project.bank.model.entity.Account;
import com.project.bank.model.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByTypeAndUserId(AccountType type, Long userId);


}
