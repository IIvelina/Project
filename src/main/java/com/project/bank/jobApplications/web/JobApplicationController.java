package com.project.bank.jobApplications.web;

import com.project.bank.jobApplications.model.dto.JobApplicationDTO;
import com.project.bank.jobApplications.service.JobApplicationService;
import com.project.bank.jobApplications.serviceModel.JobApplicationServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;
    private final ModelMapper modelMapper;

    public JobApplicationController(JobApplicationService jobApplicationService, ModelMapper modelMapper) {
        this.jobApplicationService = jobApplicationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Void> addApplication(@RequestBody JobApplicationDTO jobApplicationDTO) {
        JobApplicationServiceModel jobApplicationServiceModel = modelMapper.map(jobApplicationDTO, JobApplicationServiceModel.class);
        jobApplicationService.addApplication(jobApplicationServiceModel);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationServiceModel>> getAllApplications() {
        List<JobApplicationServiceModel> applications = jobApplicationService.findAllApplications();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationServiceModel> getApplicationById(@PathVariable Long id) {
        JobApplicationServiceModel application = jobApplicationService.findApplicationById(id);
        return ResponseEntity.ok(application);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        jobApplicationService.deleteApplication(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateApplicationStatus(@PathVariable Long id, @RequestBody JobApplicationServiceModel jobApplicationServiceModel) {
        jobApplicationServiceModel.setId(id);
        jobApplicationService.updateApplicationStatus(jobApplicationServiceModel);
        return ResponseEntity.ok().build();
    }
}
