package com.project.bank.jobApplications.service;

import com.project.bank.jobApplications.serviceModel.JobApplicationServiceModel;

import java.util.List;

public interface JobApplicationService {

    void addApplication(JobApplicationServiceModel jobApplicationServiceModel);

    List<JobApplicationServiceModel> findAllApplications();

    void deleteApplication(Long id);

    JobApplicationServiceModel findApplicationById(Long id);

    void updateApplicationStatus(JobApplicationServiceModel jobApplicationServiceModel);
}
