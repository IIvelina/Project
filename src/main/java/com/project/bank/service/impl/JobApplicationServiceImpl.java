package com.project.bank.service.impl;

import com.project.bank.model.entity.User;
import com.project.bank.model.serviceModel.JobApplicationServiceModel;
import com.project.bank.service.JobApplicationService;
import com.project.bank.service.UserService;
import com.project.bank.web.JobApplicationClient;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationClient jobApplicationClient;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public JobApplicationServiceImpl(JobApplicationClient jobApplicationClient, UserService userService, ModelMapper modelMapper) {
        this.jobApplicationClient = jobApplicationClient;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addApplication(JobApplicationServiceModel jobApplicationServiceModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);
        jobApplicationServiceModel.setUserId(currentUser.getId());
        jobApplicationClient.addApplication(jobApplicationServiceModel);
    }

    @Override
    public List<JobApplicationServiceModel> findAllApplications() {
        return jobApplicationClient.getAllApplications();
    }

    @Override
    public void deleteApplication(Long id) {
        jobApplicationClient.deleteApplication(id);
    }

    @Override
    public JobApplicationServiceModel findApplicationById(Long id) {
        JobApplicationServiceModel jobApplication = jobApplicationClient.getApplicationById(id);
        User user = userService.findById(jobApplication.getUserId());

        Hibernate.initialize(user.getRoles());

        jobApplication.setUser(user);

        return jobApplication;
    }

    @Override
    public void updateApplicationStatus(JobApplicationServiceModel jobApplicationServiceModel) {
        jobApplicationClient.updateApplicationStatus(jobApplicationServiceModel);
    }
}
