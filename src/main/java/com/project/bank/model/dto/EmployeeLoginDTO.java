package com.project.bank.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class EmployeeLoginDTO {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+_wave@financial\\.com$", message = "Invalid business email format")
    private String businessEmail;
    @NotNull
    private String password;


    public EmployeeLoginDTO() {
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}