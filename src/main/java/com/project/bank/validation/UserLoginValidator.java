//package com.project.bank.validation;
//
//import com.project.bank.model.dto.UserLoginDTO;
//import com.project.bank.repository.UserRepository;
//import jakarta.validation.Validator;
//import org.modelmapper.internal.Errors;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserLoginValidator implements Validator {
//
//    private final UserRepository userRepository;
//
//    public UserLoginValidator(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return UserLoginDTO.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        UserLoginDTO userLoginDTO = (UserLoginDTO) target;
//
//        if (!userRepository.existsByUsername(userLoginDTO.getUsername())) {
//            errors.rejectValue("username", "User not found");
//        }
//    }
//}
