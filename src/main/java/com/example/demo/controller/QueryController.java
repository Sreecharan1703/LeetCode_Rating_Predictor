package com.example.demo.controller;

import com.example.demo.dto.Data;
import com.example.demo.dto.UserData;
import com.example.demo.service.MainService;
import com.example.demo.service.userService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {
    private final userService userService;
    MainService mainService;
    QueryController(MainService mainService, userService userService) {
        this.mainService = mainService;
        this.userService = userService;
    }

    @GetMapping("/api/search")
    public Data search(@RequestParam("id") double problemId){
        int number_id = (int)problemId; //this ensures the number to convert into integer if something like '1e5' is sent.
        return mainService.getDetails(number_id);
    }

    @GetMapping("/api/user")
    public UserData getUserDetails(@RequestParam("userId") String userId){
        return userService.getUser(userId);
    }
}
