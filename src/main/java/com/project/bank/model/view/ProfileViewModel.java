package com.project.bank.model.view;

public class ProfileViewModel {
    private Long id;
    private String fullName;
    private String clientNumber;
    private String email;

    public ProfileViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Метод за получаване на първото име
    public String getFirstName() {
        if (fullName == null || fullName.isEmpty()) {
            return null;
        }
        String[] parts = fullName.split(" ");
        return parts[0];
    }

    public String getLastName() {
        if (fullName == null || fullName.isEmpty()) {
            return null;
        }
        String[] parts = fullName.split(" ");
        return parts.length > 1 ? parts[parts.length - 1] : null;
    }
}
