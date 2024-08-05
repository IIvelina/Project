package com.project.bank.service.impl;

import com.project.bank.model.entity.User;
import com.project.bank.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BankUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public BankUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(BankUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
    }

    private static UserDetails map(User user){
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                        .collect(Collectors.toList()))
                .disabled(false)
                .build();
    }
}
