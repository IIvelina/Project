package com.project.bank.service.impl;

import com.project.bank.model.entity.Role;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.UserRoleEnum;
import com.project.bank.model.serviceModel.UserServiceModel;
import com.project.bank.repository.UserRepository;

import com.project.bank.service.RoleService;
import com.project.bank.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private static final String CLIENT_NUMBER_PREFIX = "CN";
    private static final int CLIENT_NUMBER_LENGTH = 9;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;



    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;

    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

        String clientNumber = generateUniqueClientNumber();
        user.setClientNumber(clientNumber);

        Role clientRole = roleService.findRoleByRoleName(UserRoleEnum.CLIENT);
        user.setRoles(new HashSet<>(Collections.singletonList(clientRole)));
        user.setGender(userServiceModel.getGender());
        user.setUsername(userServiceModel.getUsername());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserServiceModel.class);
    }

    private String generateUniqueClientNumber() {
        String clientNumber;
        do {
            clientNumber = CLIENT_NUMBER_PREFIX + generateRandomDigits(CLIENT_NUMBER_LENGTH);
        } while (userRepository.existsByClientNumber(clientNumber));
        return clientNumber;
    }

    private String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @Override
    public UserServiceModel findByUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return modelMapper.map(user, UserServiceModel.class);
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsBySSN(String ssn) {
        return userRepository.existsBySsn(ssn);
    }

    @Override
    public boolean existsByIdCardNumber(String cardIdNumber) {
        return userRepository.existsByIdCardNumber(cardIdNumber);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }



    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    @Override
    public User getUserByUsername(String name) {
        return userRepository.findByUsername(name).orElse(null);
    }

    @Override
    public List<User> getClients() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getRole() == UserRoleEnum.CLIENT))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUserById(Long id) {
        return userRepository.findById(id);
    }



    @Override
    public Optional<User> findUserByPhoneNumber(String phone) {
        return userRepository.findByPhoneNumber(phone);
    }

    @Override
    public void addRoleToUser(User user, Role adminRole) {
        user.getRoles().add(adminRole);
        userRepository.save(user);
    }


    @Override
    public void saveUser(User user) {
    }


    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }



}
