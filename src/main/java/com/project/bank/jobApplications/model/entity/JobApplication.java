package com.project.bank.jobApplications.model.entity;

import com.project.bank.jobApplications.model.enums.ApplicationStatus;
import com.project.bank.jobApplications.model.enums.UserGenderEnum;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGenderEnum gender;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String applyingPosition;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private String address;

    private String address2;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String coverLetter;

    private String resumePath;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public JobApplication() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
