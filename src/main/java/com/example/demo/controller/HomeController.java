package com.example.demo.controller;

import com.example.demo.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    MainService mainService;
    HomeController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String homePage() {
        return "forward:/home";
    }

    @GetMapping("/home")
    public String home(){
        return "homePage";
    }

    @GetMapping("/api-error")
    public String errorPage(@RequestParam("message") String message, Model model){
        model.addAttribute("error",message);
        return "runtimeErrorPage";
    }
}
