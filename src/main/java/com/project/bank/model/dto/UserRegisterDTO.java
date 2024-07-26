package com.project.bank.model.dto;

import com.project.bank.model.enums.UserGenderEnum;
import com.project.bank.model.enums.UserRoleEnum;
import jakarta.validation.constraints.*;

public class UserRegisterDTO {
    @NotBlank(message = "SSN cannot be null or empty")
    @Size(min = 10, max = 10, message = "SSN must be exactly 10 digits")
    @Pattern(regexp = "\\d{10}", message = "SSN must be exactly 10 digits")
    private String SSN;
    @NotBlank(message = "Full name cannot be null or empty")
    @Size(min = 5, message = "Full name must be at least 5 characters long")
    @Pattern(regexp = "^[A-Z][a-z]*\\s[A-Z][a-z]*$", message = "Full name must start with a capital letter, followed by lowercase letters, a space, and another capital letter followed by lowercase letters.")
    private String fullName;
    @NotBlank(message = "Card ID number cannot be null or empty")
    @Size(min = 9, max = 9, message = "Card ID number must be exactly 9 digits")
    @Pattern(regexp = "\\d{9}", message = "Card ID number must be exactly 9 digits")
    private String cardIdNumber;


    @NotBlank(message = "Phone number cannot be null or empty")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    private String phoneNumber;
    @NotBlank(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Username cannot be null or empty")
    @Size(min = 3, max = 20, message = "Username must be between 5 and 20 symbols")
    private String username;
    @NotBlank(message = "Password cannot be null or empty")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,}$",
            message = "Password must be at least 5 characters long, and include at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;
    @NotBlank(message = "Password cannot be null or empty")
    @Size(min = 5, message = "Password must be the same")
    private String confirmPassword;
    @NotNull(message = "Gender is required.")
    private UserGenderEnum gender;

    private UserRoleEnum role;

    private String clientNumber;

    public UserRegisterDTO() {
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCardIdNumber() {
        return cardIdNumber;
    }

    public void setCardIdNumber(String cardIdNumber) {
        this.cardIdNumber = cardIdNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserGenderEnum getGender() {
        return gender;
    }

    public void setGender(UserGenderEnum gender) {
        this.gender = gender;
    }


}