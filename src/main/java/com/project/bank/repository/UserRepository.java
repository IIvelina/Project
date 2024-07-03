package com.project.bank.repository;

import com.project.bank.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  //  Optional<UserDetails> findByUsername(String username);
}

