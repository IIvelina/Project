package com.project.bank.repository;

import com.project.bank.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTop3ByToAccount_UserIdOrFromAccount_UserIdOrderByTimestampDesc(Long userId1, Long userId2);
}
