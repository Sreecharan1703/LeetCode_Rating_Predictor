package com.example.demo.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorUIController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest req, Model model){
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null){
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == 404) {
                model.addAttribute("message", "Nah, This page doesn't exist.");
            } else if (statusCode == 500) {
                model.addAttribute("message", "Internal server error. Our backend is sleeping.");
            } else {
                model.addAttribute("message", "An unexpected error occurred.");
            }
        }
        else{
            model.addAttribute("message", "Some complete unknown error from request");
        }
        return "extraErrorPage";
    }
}
