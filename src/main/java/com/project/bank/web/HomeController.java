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

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("currentUser", currentUser);
    }
}


