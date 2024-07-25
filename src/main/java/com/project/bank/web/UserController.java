package com.project.bank.web;

import com.project.bank.model.dto.UserLoginDTO;
import com.project.bank.model.dto.UserRegisterDTO;
import com.project.bank.model.entity.User;
import com.project.bank.model.serviceModel.UserServiceModel;
import com.project.bank.security.CurrentUser;
import com.project.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    //Отговаря за управлението на потребителите, тяхната регистрация и аутентикация.
    //Регистрация на нов потребител
    //Логин
    //Виждане на потребителски профил (USER)
    //Управление на потребителите (ADMIN)

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;

    }

    private final ModelMapper modelMapper;
    private final UserService userService;



    @GetMapping("/register")
    public String register(){
        return "registerEN";
    }


//    @PostMapping("/register")
//    public String registerConfirm(@Valid UserRegisterDTO userRegisterDTO,
//                                  BindingResult bindingResult,
//                                  RedirectAttributes redirectAttributes){
//        if (bindingResult.hasErrors() || !userRegisterDTO.getPassword()
//                .equals(userRegisterDTO.getConfirmPassword())){
//
//            redirectAttributes.addFlashAttribute("userRegisterDTO",
//                    userRegisterDTO);
//
//            redirectAttributes.addFlashAttribute(
//                    "org.springframework.validation.BindingResult.userRegisterDTO",
//                    bindingResult);
//
//            return "redirect:register";
//        }
//
//        userService.registerUser(modelMapper.map(userRegisterDTO, UserServiceModel.class));
//
//        return "redirect:login";
//    }

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
    public String login(Model model){
        if (!model.containsAttribute("isFound")){
            model.addAttribute("isFound", true);
        }

        return "loginEN";
    }



//    @PostMapping("/login")
//    public String loginConfirm(@Valid UserLoginDTO userLoginDTO,
//                               BindingResult bindingResult,
//                               RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("userLoginDTO",
//                    userLoginDTO);
//
//            redirectAttributes.addFlashAttribute(
//                    "org.springframework.validation.BindingResult.userLoginDTO",
//                    bindingResult);
//
//            return "redirect:login";
//        }
//        //ако няма грешки търсим дали има такъв потребител в базата
//
//        UserServiceModel userServiceModel = userService
//                .findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
//
//        //todo userNotFound
//        if (userServiceModel == null){
//            redirectAttributes.addFlashAttribute("userLoginDTO",
//                    userLoginDTO);
//            redirectAttributes.addFlashAttribute("isFound", false);
//
//            return "redirect:login";
//        }
//
//
//        //todo ако е намерен да го логнем
//        //За да го направим това първо ще направим currentUser!!!
//        userService.loginUser(userServiceModel.getId(), userLoginDTO.getUsername());
//
//        //todo на къде да бъде логнат
//        return "redirect:/";
//
//    }


    @PostMapping("/login")
    public String loginConfirm(@Valid UserLoginDTO userLoginDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginDTO",
                    userLoginDTO);

            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userLoginDTO",
                    bindingResult);

            return "redirect:login";
        }

        UserServiceModel userServiceModel = userService
                .findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());

        if (userServiceModel == null) {
            redirectAttributes.addFlashAttribute("userLoginDTO",
                    userLoginDTO);
            redirectAttributes.addFlashAttribute("isFound", false);

            return "redirect:login";
        }

        userService.loginUser(userServiceModel.getId(), userLoginDTO.getUsername());

        // Запазване на текущия потребител в сесията
        User currentUser = userService.findById(userServiceModel.getId());
        session.setAttribute("currentUser", currentUser);

        return "redirect:/";
    }

    @ModelAttribute
    public UserLoginDTO userLoginDTO(){
        return new UserLoginDTO();
    }



    @GetMapping("/logout")
        public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            return "profileEN";
        } else {
            return "redirect:/users/login";
        }
    }

    //forgot-password


    ///провери дали потребителя има роля на админ ако има трябва да вижда админ?
}
