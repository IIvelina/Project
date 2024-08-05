package com.project.bank.web;

import com.project.bank.model.serviceModel.JobApplicationServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class JobApplicationClient {

    private final RestTemplate restTemplate;

    @Autowired
    public JobApplicationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void addApplication(JobApplicationServiceModel jobApplicationServiceModel) {
        restTemplate.postForEntity("http://localhost:8081/api/job-applications", jobApplicationServiceModel, Void.class);
    }

    public List<JobApplicationServiceModel> getAllApplications() {
        JobApplicationServiceModel[] applications = restTemplate.getForObject("http://localhost:8081/api/job-applications", JobApplicationServiceModel[].class);
        return Arrays.asList(applications);
    }

    public JobApplicationServiceModel getApplicationById(Long id) {
        return restTemplate.getForObject("http://localhost:8081/api/job-applications/" + id, JobApplicationServiceModel.class);
    }

    public void deleteApplication(Long id) {
        restTemplate.delete("http://localhost:8081/api/job-applications/" + id);
    }

    public void updateApplicationStatus(JobApplicationServiceModel jobApplicationServiceModel) {
        restTemplate.put("http://localhost:8081/api/job-applications/" + jobApplicationServiceModel.getId(), jobApplicationServiceModel, Void.class);
    }
}
