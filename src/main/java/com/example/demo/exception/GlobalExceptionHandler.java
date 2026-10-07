package com.example.demo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException e, Model model){
        String message = e.getMessage();
        model.addAttribute("error", message);
        return "errorPage";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model){
        String message = e.getMessage();
        model.addAttribute("error", message);
        return "errorPage";
    }
}
