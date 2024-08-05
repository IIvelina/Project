package com.project.bank.web;

import com.project.bank.model.dto.EmployeeLoginDTO;
import com.project.bank.model.dto.JobApplicationDTO;
import com.project.bank.model.entity.Employee;
import com.project.bank.model.entity.Role;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.ApplicationStatus;
import com.project.bank.model.enums.UserRoleEnum;
import com.project.bank.model.serviceModel.JobApplicationServiceModel;
import com.project.bank.service.EmployeeService;
import com.project.bank.service.JobApplicationService;
import com.project.bank.service.RoleService;
import com.project.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JobApplicationControllerTest {

    @Mock
    private JobApplicationService jobApplicationService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private RoleService roleService;

    @Mock
    private HttpSession session;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private Model model;

    @InjectMocks
    private JobApplicationController jobApplicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDirectorDashboard() {
        when(jobApplicationService.findAllApplications()).thenReturn(new ArrayList<>());

        String viewName = jobApplicationController.directorDashboard(model);
        assertEquals("director-dashboard", viewName);
        verify(model, times(1)).addAttribute(eq("applications"), anyList());
    }

    @Test
    void testApplyForJob() {
        String viewName = jobApplicationController.applyForJob();
        assertEquals("applyForJob", viewName);
    }

    @Test
    void testJobApplyConfirm_ValidApplication() {
        JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
        JobApplicationServiceModel jobApplicationServiceModel = new JobApplicationServiceModel();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(modelMapper.map(jobApplicationDTO, JobApplicationServiceModel.class)).thenReturn(jobApplicationServiceModel);

        String viewName = jobApplicationController.jobApplyConfirm(jobApplicationDTO, bindingResult, redirectAttributes);
        assertEquals("redirect:/openPositions", viewName);
        verify(jobApplicationService, times(1)).addApplication(jobApplicationServiceModel);
    }

    @Test
    void testJobApplyConfirm_InvalidApplication() {
        JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = jobApplicationController.jobApplyConfirm(jobApplicationDTO, bindingResult, redirectAttributes);
        assertEquals("redirect:/job/apply", viewName);
        verify(jobApplicationService, never()).addApplication(any(JobApplicationServiceModel.class));
    }

    @Test
    void testDeleteJobApplication() {
        Long id = 1L;

        String viewName = jobApplicationController.deleteJobApplication(id);
        assertEquals("redirect:/director/dashboard", viewName);
        verify(jobApplicationService, times(1)).deleteApplication(id);
    }

    @Test
    void testApproveJobApplication_UserExists() {
        Long id = 1L;
        JobApplicationServiceModel jobApplication = new JobApplicationServiceModel();
        jobApplication.setPhone("12345");
        User user = new User();
        Role adminRole = new Role();
        adminRole.setRole(UserRoleEnum.ADMIN);

        when(jobApplicationService.findApplicationById(id)).thenReturn(jobApplication);
        when(userService.findUserByPhoneNumber("12345")).thenReturn(Optional.of(user));
        when(roleService.findRoleByName(UserRoleEnum.ADMIN)).thenReturn(adminRole);
        when(employeeService.existsByBusinessEmail(anyString())).thenReturn(false);

        String viewName = jobApplicationController.approveJobApplication(id, redirectAttributes);
        assertEquals("redirect:/director/dashboard", viewName);
        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
        verify(userService, times(1)).saveUser(user);
        verify(jobApplicationService, times(1)).updateApplicationStatus(jobApplication);
    }

    @Test
    void testApproveJobApplication_UserNotFound() {
        Long id = 1L;
        JobApplicationServiceModel jobApplication = new JobApplicationServiceModel();
        jobApplication.setPhone("12345");

        when(jobApplicationService.findApplicationById(id)).thenReturn(jobApplication);
        when(userService.findUserByPhoneNumber("12345")).thenReturn(Optional.empty());

        String viewName = jobApplicationController.approveJobApplication(id, redirectAttributes);
        assertEquals("redirect:/director/dashboard", viewName);
        verify(employeeService, never()).saveEmployee(any(Employee.class));
        verify(userService, never()).saveUser(any(User.class));
        verify(jobApplicationService, never()).updateApplicationStatus(any(JobApplicationServiceModel.class));
    }

    @Test
    void testApproveJobApplication_DuplicateEmail() {
        Long id = 1L;
        JobApplicationServiceModel jobApplication = new JobApplicationServiceModel();
        jobApplication.setPhone("12345");
        User user = new User();
        Role adminRole = new Role();
        adminRole.setRole(UserRoleEnum.ADMIN);

        when(jobApplicationService.findApplicationById(id)).thenReturn(jobApplication);
        when(userService.findUserByPhoneNumber("12345")).thenReturn(Optional.of(user));
        when(roleService.findRoleByName(UserRoleEnum.ADMIN)).thenReturn(adminRole);
        when(employeeService.existsByBusinessEmail(anyString())).thenReturn(true);

        String viewName = jobApplicationController.approveJobApplication(id, redirectAttributes);
        assertEquals("redirect:/director/dashboard", viewName);
        verify(redirectAttributes, times(1)).addFlashAttribute("error", "Duplicate email: " + user.getUsername() + "_wave@financial.com");
        verify(employeeService, never()).saveEmployee(any(Employee.class));
        verify(userService, never()).saveUser(any(User.class));
        verify(jobApplicationService, never()).updateApplicationStatus(any(JobApplicationServiceModel.class));
    }
}