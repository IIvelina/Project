package com.project.bank.service.impl;

import com.project.bank.model.entity.JobApplication;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.ApplicationStatus;
import com.project.bank.model.serviceModel.JobApplicationServiceModel;
import com.project.bank.repository.JobApplicationRepository;
import com.project.bank.service.JobApplicationService;
import com.project.bank.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final ModelMapper modelMapper;
    private final JobApplicationRepository jobApplicationRepository;
    private final UserService userService;

    public JobApplicationServiceImpl(ModelMapper modelMapper, JobApplicationRepository jobApplicationRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.jobApplicationRepository = jobApplicationRepository;
        this.userService = userService;
    }

    @Override
    public void addApplication(JobApplicationServiceModel jobApplicationServiceModel) {
        JobApplication jobApplication = modelMapper.map(jobApplicationServiceModel, JobApplication.class);

        // Setting the director as the first user with ID=1
        User director = userService.findById(1L);
        jobApplication.setDirector(director);

        // Setting the status to PENDING
        jobApplication.setStatus(ApplicationStatus.PENDING);

        // Fetching the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        // Setting the user who applied
        jobApplication.setUser(currentUser);

        // Saving the job application in the database
        jobApplicationRepository.save(jobApplication);
    }

    @Override
    public List<JobApplicationServiceModel> findAllApplications() {
        return jobApplicationRepository.findAll()
                .stream()
                .map(application -> modelMapper.map(application, JobApplicationServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteApplication(Long id) {
        jobApplicationRepository.deleteById(id);
    }

    @Override
    public JobApplicationServiceModel findApplicationById(Long id) {
        return jobApplicationRepository.findById(id)
                .map(app -> modelMapper.map(app, JobApplicationServiceModel.class))
                .orElse(null);
    }

    @Override
    public void updateApplicationStatus(JobApplicationServiceModel jobApplicationServiceModel) {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationServiceModel.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid job application ID: " + jobApplicationServiceModel.getId()));
        jobApplication.setStatus(jobApplicationServiceModel.getStatus());
        jobApplicationRepository.save(jobApplication);
    }
}
