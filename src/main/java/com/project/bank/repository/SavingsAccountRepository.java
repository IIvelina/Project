package com.project.bank.repository;

import com.project.bank.model.entity.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {

    List<SavingsAccount> findAllByUser_Username(String username);

    SavingsAccount findByClientNumber(String clientNumber);

    @Query("SELECT sa FROM SavingsAccount sa WHERE sa.user.username = :username")
    Optional<SavingsAccount> findByUsername(@Param("username") String username);
}
