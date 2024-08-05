package com.project.bank.jobApplications.model.dto;

import com.project.bank.jobApplications.model.enums.UserGenderEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class JobApplicationDTO {
    @NotBlank(message = "First name is required!")
    private String firstName;

    @NotBlank(message = "Last name is required!")
    private String lastName;

    @Email
    @NotBlank(message = "Email is required!")
    private String email;

    @NotNull
    private UserGenderEnum gender;

    @NotBlank(message = "Phone number is required!")
    private String phone;

    @NotBlank(message = "Position is required!")
    private String applyingPosition;

    @NotNull(message = "Starting date is required!")
    private LocalDate startDate;

    @NotBlank(message = "Address is required!")
    private String address;

    private String address2;

    @NotBlank(message = "Cover letter is required!")
    private String coverLetter;

    private String resumePath;

    private Long userId;

    public JobApplicationDTO() {
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserGenderEnum getGender() {
        return gender;
    }

    public void setGender(UserGenderEnum gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApplyingPosition() {
        return applyingPosition;
    }

    public void setApplyingPosition(String applyingPosition) {
        this.applyingPosition = applyingPosition;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
