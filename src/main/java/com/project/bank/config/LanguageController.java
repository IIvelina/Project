//package com.project.bank.config;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.servlet.LocaleResolver;
//
//import java.util.Locale;
//
//@Controller
//public class LanguageController {
//
//    private final LocaleResolver localeResolver;
//
//    public LanguageController(LocaleResolver localeResolver) {
//        this.localeResolver = localeResolver;
//    }
//
//    @GetMapping("/changeLanguage")
//    public String changeLanguage(HttpServletRequest request, HttpServletResponse response, String lang) {
//        Locale locale = new Locale(lang);
//        localeResolver.setLocale(request, response, locale);
//        return "redirect:/";
//    }
//}
