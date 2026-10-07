package com.example.demo.controller;

import com.example.demo.dto.Data;
import com.example.demo.service.MainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {
    MainService mainService;
    QueryController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/search")
    public Data search(@RequestParam double id){
        int number_id = (int)id; //this ensures the number to convert into integer if something like '1e5' is sent.
        return mainService.getDetails(number_id);
    }
}
