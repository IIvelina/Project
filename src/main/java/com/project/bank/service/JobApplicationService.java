package com.project.bank.service;

import com.project.bank.model.serviceModel.JobApplicationServiceModel;
import java.util.List;

public interface JobApplicationService {
    void addApplication(JobApplicationServiceModel jobApplicationServiceModel);
    List<JobApplicationServiceModel> findAllApplications();
    void deleteApplication(Long id);
    JobApplicationServiceModel findApplicationById(Long id);
    void updateApplicationStatus(JobApplicationServiceModel jobApplicationServiceModel);
}
