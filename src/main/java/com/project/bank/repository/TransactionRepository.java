package com.project.bank.repository;

import com.project.bank.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTop3ByFromAccount_UserIdOrderByTimestampDesc(Long userId);

//    @Query(value = "SELECT t.* FROM transactions t " +
//            "JOIN accounts a ON t.from_account_id = a.id OR t.to_account_id = a.id " +
//            "JOIN users u ON a.user_id = u.id " +
//            "WHERE u.client_number = :clientNumber " +
//            "ORDER BY t.timestamp DESC " +
//            "LIMIT 3", nativeQuery = true)
//    List<Transaction> findLastThreeTransactionsByClientNumber(@Param("clientNumber") String clientNumber);

}
