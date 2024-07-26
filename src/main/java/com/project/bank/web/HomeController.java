package com.project.bank.web;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {



    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("about")
    public String aboutUs(){
        return "about";
    }

    @GetMapping("security-tips")
    public String securityTips(){
        return "securityTips";
    }
    @GetMapping("terms")
    public String terms(){
        return "terms";
    }

    //forgot-password
    @GetMapping("user/forgot-password")
    public String forgotPassword(){
        return "forgotPassword";
    }



    @GetMapping("/productAndService")
    public String productAndService(){
        return "service";
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
}


