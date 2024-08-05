package com.project.bank.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@Controller
@RequestMapping("/locale")
public class LocaleController {

    @GetMapping("/change")
    public String changeLocale(HttpServletRequest request, HttpServletResponse response, String lang) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver != null) {
            if ("en".equals(lang)) {
                localeResolver.setLocale(request, response, Locale.ENGLISH);
            } else if ("bg".equals(lang)) {
                localeResolver.setLocale(request, response, new Locale("bg", "BG"));
            }
        }
        return "redirect:" + request.getHeader("Referer");
    }
}
