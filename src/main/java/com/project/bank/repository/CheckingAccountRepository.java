package com.project.bank.repository;

import com.project.bank.model.entity.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
    List<CheckingAccount> findAllByUserUsername(String username);

    List<CheckingAccount> findAllByUser_Username(String username);

    CheckingAccount findByClientNumber(String clientNumber);

    @Query("SELECT ca FROM CheckingAccount ca WHERE ca.user.username = :username")
    Optional<CheckingAccount> findByUsername(@Param("username") String username);
}
