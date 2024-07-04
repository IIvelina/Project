package com.project.bank.model.entity;

import com.project.bank.model.enums.ApplicationStatus;
import com.project.bank.model.enums.UserGenderEnum;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "job_applications")
public class ApplyForJob extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserGenderEnum gender;

    private String applyingPosition;
    private LocalDate startDate;
    private String address;
    private String coverLetter;
    private String resumePath;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private User director;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;



    public ApplyForJob() {
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
}
