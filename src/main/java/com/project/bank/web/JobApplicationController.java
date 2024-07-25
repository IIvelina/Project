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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class JobApplicationController {
    //Отговаря за управлението на кандидатурите за работа.
    //Подаване на нова кандидатура
    //Одобрение или отхвърляне на кандидатури (DIRECTOR)
    //Проверка дали кандидатът е вече регистриран потребител

    private final JobApplicationService jobApplicationService;
    private final ModelMapper modelMapper;

    private final UserService userService;

    private final RoleService roleService;

    private final EmployeeService employeeService;

    public JobApplicationController(JobApplicationService jobApplicationService, ModelMapper modelMapper, UserService userService, RoleService roleService, EmployeeService employeeService) {
        this.jobApplicationService = jobApplicationService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.employeeService = employeeService;
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
                                  RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("jobApplicationDTO", jobApplicationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.jobApplicationDTO", bindingResult);
            return "redirect:/job/apply";
        }

        jobApplicationService.addApplication(modelMapper.map(jobApplicationDTO, JobApplicationServiceModel.class));
        return "redirect:/openPositions";
    }

    @ModelAttribute
    public JobApplicationDTO jobApplicationDTO(){
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

//    @PostMapping("/job/approve/{id}")
//    public String approveJobApplication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        JobApplicationServiceModel jobApplication = jobApplicationService.findApplicationById(id);
//        Optional<User> optionalUser = userService.findUserByPhoneNumber(jobApplication.getPhone());
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            Role adminRole = roleService.findRoleByName(UserRoleEnum.ADMIN);
//            user.getRoles().add(adminRole);
//            userService.saveUser(user);
//
//            String businessEmail = user.getUsername() + "_wave@financial.com";
//
//            if (employeeService.existsByBusinessEmail(businessEmail)) {
//                redirectAttributes.addFlashAttribute("error", "Duplicate email: " + businessEmail);
//                return "redirect:/director/dashboard";
//            }
//
//            Employee employee = new Employee();
//            employee.setBusinessEmail(businessEmail);
//            employee.setPassword("topsicret");
//            employee.setRole(UserRoleEnum.ADMIN);
//            employee.setUser(user);
//
//            employeeService.saveEmployee(employee);
//
//            // Промяна на статуса на кандидатурата
//            jobApplication.setStatus(ApplicationStatus.APPROVED);
//            jobApplicationService.updateApplicationStatus(jobApplication);
//        } else {
//            redirectAttributes.addFlashAttribute("error", "User not found for phone number: " + jobApplication.getPhone());
//        }
//
//        return "redirect:/director/dashboard";
//    }


//    @PostMapping("/job/approve/{id}")
//    public String approveJobApplication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        JobApplicationServiceModel jobApplication = jobApplicationService.findApplicationById(id);
//        Optional<User> optionalUser = userService.findUserByPhoneNumber(jobApplication.getPhone());
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            Role adminRole = roleService.findRoleByName(UserRoleEnum.ADMIN);
//
//            // Проверка дали потребителят вече има ролята ADMIN
//            if (!user.getRoles().contains(adminRole)) {
//                userService.addRoleToUser(user, adminRole);
//            }
//
//            String businessEmail = user.getUsername() + "_wave@financial.com";
//
//            if (employeeService.existsByBusinessEmail(businessEmail)) {
//                redirectAttributes.addFlashAttribute("error", "Duplicate email: " + businessEmail);
//                return "redirect:/director/dashboard";
//            }
//
//            Employee employee = new Employee();
//            employee.setBusinessEmail(businessEmail);
//            employee.setPassword("topsicret");
//            employee.setRole(UserRoleEnum.ADMIN);
//            employee.setUser(user);
//
//            employeeService.saveEmployee(employee);
//
//            // Промяна на статуса на кандидатурата
//            jobApplication.setStatus(ApplicationStatus.APPROVED);
//            jobApplicationService.updateApplicationStatus(jobApplication);
//        } else {
//            redirectAttributes.addFlashAttribute("error", "User not found for phone number: " + jobApplication.getPhone());
//        }
//
//        return "redirect:/director/dashboard";
//    }

//    @PostMapping("/job/approve/{id}")
//    public String approveJobApplication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        JobApplicationServiceModel jobApplication = jobApplicationService.findApplicationById(id);
//        Optional<User> optionalUser = userService.findUserByPhoneNumber(jobApplication.getPhone());
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            Role adminRole = roleService.findRoleByName(UserRoleEnum.ADMIN);
//
//            // Проверка дали потребителят вече има ролята ADMIN
//            if (!user.getRoles().contains(adminRole)) {
//                userService.addRoleToUser(user, adminRole);
//            }
//
//            String businessEmail = user.getUsername() + "_wave@financial.com";
//
//            if (employeeService.existsByBusinessEmail(businessEmail)) {
//                redirectAttributes.addFlashAttribute("error", "Duplicate email: " + businessEmail);
//                return "redirect:/director/dashboard";
//            }
//
//            Employee employee = new Employee();
//            employee.setBusinessEmail(businessEmail);
//            employee.setPassword("topsicret");
//            employee.setRole(UserRoleEnum.ADMIN);
//
//            // Запазване на новия служител
//            employeeService.saveEmployee(employee);
//
//            // Обновяване на потребителя с employee_id
//            user.setEmployee(employee);
//            userService.saveUser(user);
//
//            // Промяна на статуса на кандидатурата
//            jobApplication.setStatus(ApplicationStatus.APPROVED);
//            jobApplicationService.updateApplicationStatus(jobApplication);
//        } else {
//            redirectAttributes.addFlashAttribute("error", "User not found for phone number: " + jobApplication.getPhone());
//        }
//
//        return "redirect:/director/dashboard";
//    }

    @PostMapping("/job/approve/{id}")
    public String approveJobApplication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        JobApplicationServiceModel jobApplication = jobApplicationService.findApplicationById(id);
        Optional<User> optionalUser = userService.findUserByPhoneNumber(jobApplication.getPhone());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Role adminRole = roleService.findRoleByName(UserRoleEnum.ADMIN);

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
            employee.setRole(UserRoleEnum.ADMIN);
            employee.setUser(user);

            // Запазване на новия служител
            employeeService.saveEmployee(employee);

            // Обновяване на потребителя с employee_id
          //  user.setEmployee(employee);
            userService.saveUser(user);

            // Промяна на статуса на кандидатурата
            jobApplication.setStatus(ApplicationStatus.APPROVED);
            jobApplicationService.updateApplicationStatus(jobApplication);
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found for phone number: " + jobApplication.getPhone());
        }

        return "redirect:/director/dashboard";
    }



}
