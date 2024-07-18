package com.project.bank.service;

import com.project.bank.model.entity.User;
import com.project.bank.model.serviceModel.UserServiceModel;

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
}
