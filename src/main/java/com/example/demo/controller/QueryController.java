package com.example.demo.controller;

import com.example.demo.dto.Data;
import com.example.demo.dto.UserData;
import com.example.demo.entity.Question;
import com.example.demo.service.MainService;
import com.example.demo.service.userService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/api/filter")
    public List<Question> getQuestionsInRange(@RequestParam("minRange") int minRange, @RequestParam("maxRange") int maxRange,@RequestParam("pageNumber") int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1,10);
        return userService.getQuestionsInRange(minRange, maxRange, pageable);
    }
}
