package com.project.bank.service.impl;

import com.project.bank.model.entity.JobApplication;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.ApplicationStatus;
import com.project.bank.model.serviceModel.JobApplicationServiceModel;
import com.project.bank.repository.JobApplicationRepository;
import com.project.bank.repository.RoleRepository;
import com.project.bank.security.CurrentUser;
import com.project.bank.service.JobApplicationService;
import com.project.bank.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final ModelMapper modelMapper;
    private final JobApplicationRepository jobApplicationRepository;

    private final UserService userService;
    private final CurrentUser currentUser;


    public JobApplicationServiceImpl(ModelMapper modelMapper, JobApplicationRepository jobApplicationRepository, UserService userService, CurrentUser currentUser) {
        this.modelMapper = modelMapper;
        this.jobApplicationRepository = jobApplicationRepository;
        this.userService = userService;
        this.currentUser = currentUser;

    }

    @Override
    public void addApplication(JobApplicationServiceModel jobApplicationServiceModel) {
        JobApplication jobApplication = modelMapper.map(jobApplicationServiceModel, JobApplication.class);

        // Задаване на директора, който е първият потребител с ID=1
        User director = userService.findById(1L);
        jobApplication.setDirector(director);

        // Задаване на статуса на PENDING
        jobApplication.setStatus(ApplicationStatus.PENDING);

        jobApplication.setUser(userService.findById(currentUser.getId()));

        // Запазване на кандидатурата в базата данни
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
