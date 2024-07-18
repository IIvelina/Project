package com.project.bank.service;

import com.project.bank.model.entity.User;
import com.project.bank.model.serviceModel.UserServiceModel;

public interface UserService {
    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsernameAndPassword(String username, String password);

    void loginUser(Long id, String username);

    User findById(Long id);
}
