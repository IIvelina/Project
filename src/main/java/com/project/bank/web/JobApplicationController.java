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
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final EmployeeService employeeService;
    private final RoleService roleService;

    public JobApplicationController(JobApplicationService jobApplicationService, ModelMapper modelMapper, UserService userService, EmployeeService employeeService, RoleService roleService) {
        this.jobApplicationService = jobApplicationService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @GetMapping("/director/dashboard")
    public String directorDashboard(Model model) {
        model.addAttribute("applications", jobApplicationService.findAllApplications());
        return "director-dashboard";
    }

    @GetMapping("/job/apply")
    public String applyForJob() {
        return "applyForJob";
    }

    @PostMapping("/job/apply")
    public String jobApplyConfirm(@Valid @ModelAttribute("jobApplicationDTO") JobApplicationDTO jobApplicationDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("jobApplicationDTO", jobApplicationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.jobApplicationDTO", bindingResult);
            return "redirect:/job/apply";
        }

        JobApplicationServiceModel jobApplicationServiceModel = modelMapper.map(jobApplicationDTO, JobApplicationServiceModel.class);
        jobApplicationService.addApplication(jobApplicationServiceModel);

        return "redirect:/openPositions";
    }

    @ModelAttribute
    public JobApplicationDTO jobApplicationDTO() {
        return new JobApplicationDTO();
    }

    @ModelAttribute
    public EmployeeLoginDTO employeeLoginDTO() {
        return new EmployeeLoginDTO();
    }

    @DeleteMapping("/job/delete/{id}")
    public String deleteJobApplication(@PathVariable Long id) {
        jobApplicationService.deleteApplication(id);
        return "redirect:/director/dashboard";
    }

    @PostMapping("/job/approve/{id}")
    public String approveJobApplication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        JobApplicationServiceModel jobApplication = jobApplicationService.findApplicationById(id);
        Optional<User> optionalUser = userService.findUserByPhoneNumber(jobApplication.getPhone());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Role adminRole = roleService.findRoleByName(UserRoleEnum.ADMIN);


            Hibernate.initialize(user.getRoles());

            // Проверка дали потребителят вече има ролята ADMIN
            if (!user.getRoles().contains(adminRole)) {
                userService.addRoleToUser(user, adminRole);
            }

            String businessEmail = user.getUsername() + "_wave@financial.com";

            if (employeeService.existsByBusinessEmail(businessEmail)) {
                redirectAttributes.addFlashAttribute("error", "Duplicate email: " + businessEmail);
                return "redirect:/director/dashboard";
            }

            Employee employee = new Employee();
            employee.setBusinessEmail(businessEmail);
            employee.setPassword("topsicret");
            employee.setRole(UserRoleEnum.ADMIN); // This field should not be unique now
            employee.setUser(user);

            // Запазване на новия служител
            try {
                employeeService.saveEmployee(employee);
            } catch (DataIntegrityViolationException e) {
                redirectAttributes.addFlashAttribute("error", "Failed to save employee: " + e.getMessage());
                return "redirect:/director/dashboard";
            }

   
            userService.saveUser(user);

  
            jobApplication.setStatus(ApplicationStatus.APPROVED);
            jobApplicationService.updateApplicationStatus(jobApplication);
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found for phone number: " + jobApplication.getPhone());
        }

        return "redirect:/director/dashboard";
    }
}
