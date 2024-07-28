package com.project.bank.web;

import com.project.bank.model.dto.UserLoginDTO;
import com.project.bank.model.dto.UserRegisterDTO;
import com.project.bank.model.entity.Employee;
import com.project.bank.model.entity.User;
import com.project.bank.model.serviceModel.UserServiceModel;
import com.project.bank.service.EmployeeService;
import com.project.bank.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final EmployeeService employeeService;

    public UserController(ModelMapper modelMapper, UserService userService, EmployeeService employeeService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @GetMapping("/register")
    public String register(){
        return "registerEN";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterDTO userRegisterDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        boolean hasError = false;

        if (bindingResult.hasErrors() || !userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            hasError = true;
        }

        if (userService.existsBySSN(userRegisterDTO.getSSN())) {
            bindingResult.rejectValue("SSN", "error.userRegisterDTO", "This SSN is already in use.");
            hasError = true;
        }

        if (userService.existsByIdCardNumber(userRegisterDTO.getCardIdNumber())) {
            bindingResult.rejectValue("cardIdNumber", "error.userRegisterDTO", "This ID Card Number is already in use.");
            hasError = true;
        }

        if (userService.existsByEmail(userRegisterDTO.getEmail())) {
            bindingResult.rejectValue("email", "error.userRegisterDTO", "This email is already in use.");
            hasError = true;
        }

        if (userService.existsByUsername(userRegisterDTO.getUsername())) {
            bindingResult.rejectValue("username", "error.userRegisterDTO", "This username is already in use.");
            hasError = true;
        }

        if (userService.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())) {
            bindingResult.rejectValue("phoneNumber", "error.userRegisterDTO", "This phone number is already in use.");
            hasError = true;
        }

        if (hasError) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:register";
        }

        userService.registerUser(modelMapper.map(userRegisterDTO, UserServiceModel.class));
        return "redirect:login";
    }

    @ModelAttribute
    public UserRegisterDTO userRegisterDTO(){
        return new UserRegisterDTO();
    }


    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        if (!model.containsAttribute("isFound")) {
            model.addAttribute("isFound", true);
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            model.addAttribute("session", session);
        }

        return "loginEN";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid @ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userLoginDTO",
                    bindingResult);
            return "redirect:login";
        }

        UserServiceModel userServiceModel = userService
                .findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());

        if (userServiceModel == null) {
            if (!userService.existsByUsername(userLoginDTO.getUsername())) {
                redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
                redirectAttributes.addFlashAttribute("isFound", false);
                redirectAttributes.addFlashAttribute("usernameNotFound", true);
                return "redirect:login";
            } else {
                redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
                redirectAttributes.addFlashAttribute("isFound", false);
                return "redirect:login";
            }
        }

        // Save current user in session
        User currentUserById = userService.findById(userServiceModel.getId());
        session.setAttribute("currentUser", currentUserById);

        // Check if the user has a business email and should see the admin button
        User currentUserByUsername = userService.getUserByUsername(userServiceModel.getUsername());
        String currentUserUsername = currentUserByUsername.getUsername();
        String businessEmail = currentUserUsername + "_wave@financial.com";
        Optional<Employee> employee = employeeService.findByBusinessEmail(businessEmail);

        session.setAttribute("isAdmin", employee.isPresent());
        return "redirect:/";
    }

    @ModelAttribute
    public UserLoginDTO userLoginDTO(){
        return new UserLoginDTO();
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("isFound", false);
        return "loginEN";
    }

//    @GetMapping("/login")
//    public String login(Model model, HttpServletRequest request) {
//        if (!model.containsAttribute("isFound")) {
//            model.addAttribute("isFound", true);
//        }
//
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            model.addAttribute("session", session);
//        }
//
//        return "loginEN";
//    }
//
//
//
//
//
//
//
//
//
////
////
////
//    @PostMapping("/login")
//    public String loginConfirm(@Valid UserLoginDTO userLoginDTO,
//                               BindingResult bindingResult,
//                               RedirectAttributes redirectAttributes,
//                               HttpSession session) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("userLoginDTO",
//                    userLoginDTO);
//
//            redirectAttributes.addFlashAttribute(
//                    "org.springframework.validation.BindingResult.userLoginDTO",
//                    bindingResult);
//
//
//
//            return "redirect:login";
//        }
//
//        UserServiceModel userServiceModel = userService
//                .findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
//
//        if (userServiceModel == null) {
//            redirectAttributes.addFlashAttribute("userLoginDTO",
//                    userLoginDTO);
//            redirectAttributes.addFlashAttribute("isFound", false);
//
//            return "redirect:login";
//        }
//
//
//        //???????????
//      //  userService.loginUser(userServiceModel.getId(), userLoginDTO.getUsername());
//
//        // Запазване на текущия потребител в сесията
//        User currentUserById = userService.findById(userServiceModel.getId());
//        session.setAttribute("currentUser", currentUserById);
//
//        //АКО ТОЗИ ПОТРЕБИТЕЛ ИМА БИЗНЕС ИМЕЛ ТОЙ ТРЯБВА ДА ВИЖДА БУТОНА ADMIN
//
//        User currentUserByUsername = userService.getUserByUsername(userServiceModel.getUsername());
//        String currentUserUsername = currentUserByUsername.getUsername();
//        String businessEmail = currentUserUsername + "_wave@financial.com";
//        Optional<Employee> employee = employeeService.findByBusinessEmail(businessEmail);
//
//        session.setAttribute("isAdmin", employee.isPresent());
//        return "redirect:/";
//    }
//
//
//
//
//
//
//
//
//
//
//    @ModelAttribute
//    public UserLoginDTO userLoginDTO(){
//        return new UserLoginDTO();
//    }
//
//    @GetMapping("/login-error")
//    public String loginError(Model model) {
//        model.addAttribute("isFound", false);
//        return "loginEN";
//    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        User currentUser = userService.findByUsername(principal.getUsername());
        model.addAttribute("currentUser", currentUser);
        return "profileEN";
    }
}



//
//@Controller
//@RequestMapping("/users")
//public class UserController {
//
//    //Отговаря за управлението на потребителите, тяхната регистрация и аутентикация.
//    //
//    //Регистрация на нов потребител
//    //Логин
//    //Виждане на потребителски профил (USER)
//    //Управление на потребителите (ADMIN)
//    //Добавяне и премахване на роли
//
//    public UserController(ModelMapper modelMapper, UserService userService) {
//        this.modelMapper = modelMapper;
//        this.userService = userService;
//
//    }
//
//    private final ModelMapper modelMapper;
//    private final UserService userService;
//
//
//
//    @GetMapping("/register")
//    public String register(){
//        return "registerEN";
//    }
//
//
////    @PostMapping("/register")
////    public String registerConfirm(@Valid UserRegisterDTO userRegisterDTO,
////                                  BindingResult bindingResult,
////                                  RedirectAttributes redirectAttributes){
////        if (bindingResult.hasErrors() || !userRegisterDTO.getPassword()
////                .equals(userRegisterDTO.getConfirmPassword())){
////
////            redirectAttributes.addFlashAttribute("userRegisterDTO",
////                    userRegisterDTO);
////
////            redirectAttributes.addFlashAttribute(
////                    "org.springframework.validation.BindingResult.userRegisterDTO",
////                    bindingResult);
////
////            return "redirect:register";
////        }
////
////        userService.registerUser(modelMapper.map(userRegisterDTO, UserServiceModel.class));
////
////        return "redirect:login";
////    }
//
//    @PostMapping("/register")
//    public String registerConfirm(@Valid UserRegisterDTO userRegisterDTO,
//                                  BindingResult bindingResult,
//                                  RedirectAttributes redirectAttributes) {
//        boolean hasError = false;
//
//        if (bindingResult.hasErrors() || !userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
//            hasError = true;
//        }
//
//        if (userService.existsBySSN(userRegisterDTO.getSSN())) {
//            bindingResult.rejectValue("SSN", "error.userRegisterDTO", "This SSN is already in use.");
//            hasError = true;
//        }
//
//        if (userService.existsByIdCardNumber(userRegisterDTO.getCardIdNumber())) {
//            bindingResult.rejectValue("cardIdNumber", "error.userRegisterDTO", "This ID Card Number is already in use.");
//            hasError = true;
//        }
//
//        if (userService.existsByEmail(userRegisterDTO.getEmail())) {
//            bindingResult.rejectValue("email", "error.userRegisterDTO", "This email is already in use.");
//            hasError = true;
//        }
//
//        if (userService.existsByUsername(userRegisterDTO.getUsername())) {
//            bindingResult.rejectValue("username", "error.userRegisterDTO", "This username is already in use.");
//            hasError = true;
//        }
//
//        if (userService.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())) {
//            bindingResult.rejectValue("phoneNumber", "error.userRegisterDTO", "This phone number is already in use.");
//            hasError = true;
//        }
//
//        if (hasError) {
//            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
//            return "redirect:register";
//        }
//
//        userService.registerUser(modelMapper.map(userRegisterDTO, UserServiceModel.class));
//        return "redirect:login";
//    }
//
//
//
//    @ModelAttribute
//    public UserRegisterDTO userRegisterDTO(){
//        return new UserRegisterDTO();
//    }
//
//
//    @GetMapping("/login")
//    public String login(Model model){
//        if (!model.containsAttribute("isFound")){
//            model.addAttribute("isFound", true);
//        }
//
//        return "loginEN";
//    }
//
//
//
////    @PostMapping("/login")
////    public String loginConfirm(@Valid UserLoginDTO userLoginDTO,
////                               BindingResult bindingResult,
////                               RedirectAttributes redirectAttributes) {
////        if (bindingResult.hasErrors()) {
////            redirectAttributes.addFlashAttribute("userLoginDTO",
////                    userLoginDTO);
////
////            redirectAttributes.addFlashAttribute(
////                    "org.springframework.validation.BindingResult.userLoginDTO",
////                    bindingResult);
////
////            return "redirect:login";
////        }
////        //ако няма грешки търсим дали има такъв потребител в базата
////
////        UserServiceModel userServiceModel = userService
////                .findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
////
////        //todo userNotFound
////        if (userServiceModel == null){
////            redirectAttributes.addFlashAttribute("userLoginDTO",
////                    userLoginDTO);
////            redirectAttributes.addFlashAttribute("isFound", false);
////
////            return "redirect:login";
////        }
////
////
////        //todo ако е намерен да го логнем
////        //За да го направим това първо ще направим currentUser!!!
////        userService.loginUser(userServiceModel.getId(), userLoginDTO.getUsername());
////
////        //todo на къде да бъде логнат
////        return "redirect:/";
////
////    }
//
//
////    @PostMapping("/login")
////    public String loginConfirm(@Valid UserLoginDTO userLoginDTO,
////                               BindingResult bindingResult,
////                               RedirectAttributes redirectAttributes,
////                               HttpSession session) {
////        if (bindingResult.hasErrors()) {
////            redirectAttributes.addFlashAttribute("userLoginDTO",
////                    userLoginDTO);
////
////            redirectAttributes.addFlashAttribute(
////                    "org.springframework.validation.BindingResult.userLoginDTO",
////                    bindingResult);
////
////            return "redirect:login";
////        }
////
////        UserServiceModel userServiceModel = userService
////                .findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
////
////        if (userServiceModel == null) {
////            redirectAttributes.addFlashAttribute("userLoginDTO",
////                    userLoginDTO);
////            redirectAttributes.addFlashAttribute("isFound", false);
////
////            return "redirect:login";
////        }
////
////        userService.loginUser(userServiceModel.getId(), userLoginDTO.getUsername());
////
////        // Запазване на текущия потребител в сесията
////        User currentUser = userService.findById(userServiceModel.getId());
////        session.setAttribute("currentUser", currentUser);
////
////        return "redirect:/";
////    }
//
//    @ModelAttribute
//    public UserLoginDTO userLoginDTO(){
//        return new UserLoginDTO();
//    }
//
//
//
//    @GetMapping("/logout")
//        public String logout(HttpSession httpSession){
//        httpSession.invalidate();
//        return "redirect:/";
//    }
//
//    @GetMapping("/profile")
//    public String profile(HttpSession session, Model model) {
//        User currentUser = (User) session.getAttribute("currentUser");
//        if (currentUser != null) {
//            model.addAttribute("currentUser", currentUser);
//            return "profileEN";
//        } else {
//            return "redirect:/users/login";
//        }
//    }
//}

