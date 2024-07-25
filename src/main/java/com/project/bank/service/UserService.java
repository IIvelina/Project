package com.project.bank.service;

import com.project.bank.model.entity.Role;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.UserRoleEnum;
import com.project.bank.model.serviceModel.UserServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsernameAndPassword(String username, String password);

    void loginUser(Long id, String username);

    User findById(Long id);

    boolean existsBySSN(String ssn);

    boolean existsByIdCardNumber(String cardIdNumber);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    User findByUsername(String username);

    User save(User user);


    User getUserByPhoneNumber(String recipientPhoneNumber);


    void saveUser(User user);


    Optional<User> findUserByPhoneNumber(String phone);

    void addRoleToUser(User user, Role adminRole);

    boolean hasRole(Long id, UserRoleEnum userRoleEnum);


    List<User> getClients();

    void delete(User user);

    Optional<User> findByUserById(Long id);
}
