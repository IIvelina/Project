package com.project.bank.jobApplications.service.impl;

import com.project.bank.jobApplications.model.entity.JobApplication;
import com.project.bank.jobApplications.repository.JobApplicationRepository;
import com.project.bank.jobApplications.service.JobApplicationService;
import com.project.bank.jobApplications.serviceModel.JobApplicationServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final ModelMapper modelMapper;

    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, ModelMapper modelMapper) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addApplication(JobApplicationServiceModel jobApplicationServiceModel) {
        JobApplication jobApplication = modelMapper.map(jobApplicationServiceModel, JobApplication.class);
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
    public JobApplicationServiceModel findApplicationById(Long id) {
        JobApplication jobApplication = jobApplicationRepository.findById(id).orElse(null);
        return modelMapper.map(jobApplication, JobApplicationServiceModel.class);
    }

    @Override
    public void deleteApplication(Long id) {
        jobApplicationRepository.deleteById(id);
    }

    @Override
    public void updateApplicationStatus(JobApplicationServiceModel jobApplicationServiceModel) {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationServiceModel.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid job application ID: " + jobApplicationServiceModel.getId()));
        jobApplication.setStatus(jobApplicationServiceModel.getStatus());
        jobApplicationRepository.save(jobApplication);
    }
}
