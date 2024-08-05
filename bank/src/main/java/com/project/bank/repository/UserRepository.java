package com.project.bank.repository;

import com.project.bank.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByClientNumber(String clientNumber);
    Optional<User> findByUsername(String username);

    boolean existsBySsn(String ssn);
    boolean existsByIdCardNumber(String idCardNumber);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);

}
