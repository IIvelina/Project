package com.project.bank.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {

        ex.printStackTrace();

        model.addAttribute("errorMessage", "Oops...  You cannot delete the user because he has accounts!");

        return "error";
    }
}