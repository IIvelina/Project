package com.project.bank.web;

import com.project.bank.model.dto.EmployeeLoginDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JobApplicationController {
    //Отговаря за управлението на кандидатурите за работа.
    //Подаване на нова кандидатура
    //Одобрение или отхвърляне на кандидатури (DIRECTOR)
    //Проверка дали кандидатът е вече регистриран потребител


    @GetMapping("/director/dashboard")
    public String directorDashboard() {
        return "director-dashboard";
    }

    @GetMapping("/job/apply")
    public String applyForJob() {
        return "applyForJob";
    }

    @ModelAttribute
    public EmployeeLoginDTO employeeLoginDTO() {
        return new EmployeeLoginDTO();
    }
}
