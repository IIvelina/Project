package com.project.bank.web;

import com.project.bank.model.dto.UserRegisterDTO;
import com.project.bank.model.serviceModel.UserServiceModel;
import com.project.bank.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

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


    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterDTO userRegisterDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors() || !userRegisterDTO.getPassword()
                .equals(userRegisterDTO.getConfirmPassword())){

            redirectAttributes.addFlashAttribute("userRegisterDTO",
                    userRegisterDTO);

            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterDTO",
                    bindingResult);

            return "redirect:register";
        }

        //save in db
        //правя метод registerUser

        userService.registerUser(modelMapper.map(userRegisterDTO, UserServiceModel.class));

        return "redirect:login";
    }


    @GetMapping("/login")
    public String login(){
        return "loginEN";
    }

    @ModelAttribute
    public UserRegisterDTO userRegisterDTO(){
        return new UserRegisterDTO();
    }

}
