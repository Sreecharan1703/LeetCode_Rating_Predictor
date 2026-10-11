package com.example.demo.controller;

import com.example.demo.dto.Data;
import com.example.demo.dto.UserData;
import com.example.demo.dto.ViewDto;
import com.example.demo.entity.Question;
import com.example.demo.service.MainService;
import com.example.demo.service.userService;
import com.example.demo.service.ViewService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QueryController {
    private final userService userService;
    private final MainService mainService;
    private final ViewService viewService;

    QueryController(MainService mainService, userService userService, ViewService viewService) {
        this.mainService = mainService;
        this.userService = userService;
        this.viewService = viewService;
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

    @PostMapping("/api/views")
    public ViewDto updateViews(){
        return viewService.incrementAndGetView();
    }

    @GetMapping("/api/views")
    public ViewDto getViews(){
        return viewService.getView();
    }
}
