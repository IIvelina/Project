//package com.project.bank.service.impl;
//
//import com.project.bank.model.entity.User;
//import com.project.bank.repository.UserRepository;
//
//
//import java.util.List;
//import java.util.Optional;
//
//public class BankUserDetailsService implements UserDetailsService {
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
//
//      private final UserRepository userRepository;
//
//    public BankUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username)
//                .map(BankUserDetailsService::map)
//                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
//    }
//
//    private static UserDetails map(User user){
//        return UserEntity
//                .withUsername(user.getUsername())
//                .password(user.getPassword())
//                .authorities(List.of())/*todo*/
//                .build();
//
//    }
//}
