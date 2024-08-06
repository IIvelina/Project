package com.project.bank.web;

import com.project.bank.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                        Model model){
        if (userDetails != null) {
            String username = capitalizeFirstLetter(userDetails.getUsername());
            model.addAttribute("welcomeMessage", username);
        } else {
            model.addAttribute("welcomeMessage", "Anonymous");
        }
        return "index";
    }



    private String capitalizeFirstLetter(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
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


