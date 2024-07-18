package com.project.bank.web;

import com.project.bank.model.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class RoleController {
    //Отговаря за управлението на ролите.
    //
    //Добавяне на нови роли
    //Присвояване на роли на потребители
    //Премахване на роли от потребители
    //profile/user

//    @GetMapping("/user")
//    public String profile(){
//        return "profileEN";
//    }


}
