package com.project.bank.model.entity;

import com.project.bank.model.enums.ApplicationStatus;
import com.project.bank.model.enums.UserGenderEnum;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "job_applications")
public class JobApplication extends BaseEntity {
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

    @ManyToOne
    @JoinColumn(name = "director_id")
    private User director;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    @OneToOne
    private User user;

    public JobApplication() {
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

    public User getDirector() {
        return director;
    }

    public void setDirector(User director) {
        this.director = director;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }
}