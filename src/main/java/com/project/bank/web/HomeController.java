package com.project.bank.web;

import com.project.bank.security.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class HomeController {

    private final CurrentUser currentUser;

    public HomeController(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("about")
    public String aboutUs(){
        return "about";
    }


    @GetMapping("terms")
    public String terms(){
        return "terms";
    }



    @GetMapping("security-tips")
    public String securityTips(){
        return "securityTips";
    }


    @GetMapping("/applicationProcess")
    public String applicationProcess(){
        return "application-process";
    }

    @GetMapping("/benefits")
    public String benefits(){
        return "benefits";
    }

    @GetMapping("/openPositions")
    public String openPositions(){
        return "openPositions";
    }


    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    @GetMapping("/productAndService")
    public String productAndService(){
        return "service";
    }
    @GetMapping("/cards")
    public String cards(){
        return "cards";
    }

    @GetMapping("/loans")
    public String loans(){
        return "loans";
    }

    @GetMapping("/savings")
    public String savings(){
        return "savings";
    }

    @GetMapping("/user/forgot-password")
    public String forgotPassword(){
        return "forgotPassword";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("currentUser", currentUser);
    }
//
//    @GetMapping("/user/admin")
//    public String loginAsAdmin(){
//        return "loginAsAdmin";
//    }
}


