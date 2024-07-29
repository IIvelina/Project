package com.project.bank.service.impl;

import com.project.bank.model.entity.JobApplication;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.ApplicationStatus;
import com.project.bank.model.serviceModel.JobApplicationServiceModel;
import com.project.bank.repository.JobApplicationRepository;
import com.project.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobApplicationServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JobApplicationServiceImpl jobApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddApplication() {
        JobApplicationServiceModel serviceModel = new JobApplicationServiceModel();
        serviceModel.setId(1L);

        JobApplication jobApplication = new JobApplication();
        User director = new User();
        User currentUser = new User();

        when(modelMapper.map(serviceModel, JobApplication.class)).thenReturn(jobApplication);
        when(userService.findById(1L)).thenReturn(director);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("currentUser");
        when(userService.findByUsername("currentUser")).thenReturn(currentUser);

        SecurityContextHolder.setContext(securityContext);

        jobApplicationService.addApplication(serviceModel);

        assertEquals(director, jobApplication.getDirector());
        assertEquals(ApplicationStatus.PENDING, jobApplication.getStatus());
        assertEquals(currentUser, jobApplication.getUser());

        verify(jobApplicationRepository, times(1)).save(jobApplication);
    }

    @Test
    void testFindAllApplications() {
        JobApplication jobApplication = new JobApplication();
        JobApplicationServiceModel serviceModel = new JobApplicationServiceModel();

        when(jobApplicationRepository.findAll()).thenReturn(List.of(jobApplication));
        when(modelMapper.map(jobApplication, JobApplicationServiceModel.class)).thenReturn(serviceModel);

        List<JobApplicationServiceModel> result = jobApplicationService.findAllApplications();

        assertEquals(1, result.size());
        assertEquals(serviceModel, result.get(0));
    }

    @Test
    void testDeleteApplication() {
        Long id = 1L;
        jobApplicationService.deleteApplication(id);
        verify(jobApplicationRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindApplicationById() {
        Long id = 1L;
        JobApplication jobApplication = new JobApplication();
        JobApplicationServiceModel serviceModel = new JobApplicationServiceModel();

        when(jobApplicationRepository.findById(id)).thenReturn(Optional.of(jobApplication));
        when(modelMapper.map(jobApplication, JobApplicationServiceModel.class)).thenReturn(serviceModel);

        JobApplicationServiceModel result = jobApplicationService.findApplicationById(id);

        assertEquals(serviceModel, result);
    }

    @Test
    void testFindApplicationById_NotFound() {
        Long id = 1L;

        when(jobApplicationRepository.findById(id)).thenReturn(Optional.empty());

        JobApplicationServiceModel result = jobApplicationService.findApplicationById(id);

        assertNull(result);
    }

    @Test
    void testUpdateApplicationStatus() {
        Long id = 1L;
        JobApplication jobApplication = new JobApplication();
        JobApplicationServiceModel serviceModel = new JobApplicationServiceModel();
        serviceModel.setId(id);
        serviceModel.setStatus(ApplicationStatus.APPROVED);

        when(jobApplicationRepository.findById(id)).thenReturn(Optional.of(jobApplication));

        jobApplicationService.updateApplicationStatus(serviceModel);

        assertEquals(ApplicationStatus.APPROVED, jobApplication.getStatus());
        verify(jobApplicationRepository, times(1)).save(jobApplication);
    }

    @Test
    void testUpdateApplicationStatus_InvalidId() {
        Long id = 1L;
        JobApplicationServiceModel serviceModel = new JobApplicationServiceModel();
        serviceModel.setId(id);

        when(jobApplicationRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            jobApplicationService.updateApplicationStatus(serviceModel);
        });

        assertEquals("Invalid job application ID: " + id, exception.getMessage());
    }
}