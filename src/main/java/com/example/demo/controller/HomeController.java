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
        return "homePage";
    }

    @GetMapping("/search")
    public String query(@RequestParam int id, Model model) {
        try{
            mainService.getDetails(id,model);
        }
        catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "errorPage";
        }
        return "displayPage";
    }
}
