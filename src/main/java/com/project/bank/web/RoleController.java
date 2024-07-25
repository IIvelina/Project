package com.project.bank.web;

import com.project.bank.model.dto.EmployeeLoginDTO;
import com.project.bank.model.entity.Employee;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.UserRoleEnum;
import com.project.bank.service.EmployeeService;
import com.project.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class RoleController {
    //Отговаря за управлението на ролите.
    //Добавяне на нови роли
    //Присвояване на роли на потребители
    //Премахване на роли от потребители
    //profile/user

//    @GetMapping("/user")
//    public String profile(){
//        return "profileEN";
//    }

    private final EmployeeService employeeService;

    public RoleController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

//    //href="/user/director"
//    @GetMapping("director")
//    public String loginAsDirector(){
//        return "loginAsDirector";
//    }
//
//    @GetMapping("admin")
//    public String loginAsAdmin(){
//        return "loginAsAdmin";
//    }
//
//
//    @PostMapping("admin/login")
//    public String loginConfirm(@Valid @ModelAttribute("employeeLoginDTO") EmployeeLoginDTO employeeLoginDTO,
//                               BindingResult bindingResult,
//                               HttpSession session,
//                               RedirectAttributes redirectAttributes,
//                               Model model) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("employeeLoginDTO", employeeLoginDTO);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employeeLoginDTO", bindingResult);
//            return "redirect:/user/admin";
//        }
//
//        Employee employee = employeeService.authenticate(employeeLoginDTO.getBusinessEmail(), employeeLoginDTO.getPassword());
//
//        if (employee != null && employee.getRole().equals(UserRoleEnum.ADMIN)) {
//            session.setAttribute("loggedInUser", employee);
//            return "redirect:/user/admin/dashboard";
//        } else {
//            model.addAttribute("loginError", "Invalid email or password");
//            return "loginAsAdmin";
//        }
//    }
//
//
//
//
//    @ModelAttribute
//    public EmployeeLoginDTO employeeLoginDTO(){
//        return new EmployeeLoginDTO();
//    }
//
//    @GetMapping("admin/dashboard")
//    public String adminDashboard(){
//        return "admin-dashboard";
//    }

    private final UserService userService;


    @GetMapping("director")
    public String loginAsDirector(){
        return "loginAsDirector";
    }

    @GetMapping("admin")
    public String loginAsAdmin(){
        return "loginAsAdmin";
    }

    @PostMapping("admin/login")
    public String loginConfirm(@Valid @ModelAttribute("employeeLoginDTO") EmployeeLoginDTO employeeLoginDTO,
                               BindingResult bindingResult,
                               HttpSession session,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("employeeLoginDTO", employeeLoginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employeeLoginDTO", bindingResult);
            return "redirect:/user/admin";
        }

        Employee employee = employeeService.authenticate(employeeLoginDTO.getBusinessEmail(), employeeLoginDTO.getPassword());

        if (employee != null && employee.getRole().equals(UserRoleEnum.ADMIN)) {
            session.setAttribute("loggedInUser", employee);
            return "redirect:/user/admin/dashboard";
        } else {
            model.addAttribute("loginError", "Invalid email or password");
            return "loginAsAdmin";
        }
    }

    @ModelAttribute
    public EmployeeLoginDTO employeeLoginDTO(){
        return new EmployeeLoginDTO();
    }

//    @GetMapping("admin/dashboard")
//    public String adminDashboard(){
//        return "admin-dashboard";
//    }


    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        List<User> clients = employeeService.getClients();
        model.addAttribute("clients", clients);
        return "admin-dashboard";
    }


    @DeleteMapping("/admin/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userService.findByUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean hasZeroBalanceInChecking = user.getCheckingAccount() == null || user.getCheckingAccount().getBalance().compareTo(BigDecimal.ZERO) == 0;
            boolean hasZeroBalanceInSavings = user.getSavingsAccount() == null || user.getSavingsAccount().getBalance().compareTo(BigDecimal.ZERO) == 0;

            if (hasZeroBalanceInChecking && hasZeroBalanceInSavings && !employeeService.isEmployeeByEmail(user.getUsername())) {
                userService.delete(user);
                redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "User cannot be deleted. Make sure they have zero balance in both accounts and are not an employee.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
        }
        return "redirect:/user/admin/dashboard";
    }
}
