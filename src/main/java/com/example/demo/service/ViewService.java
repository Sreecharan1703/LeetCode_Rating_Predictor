package com.example.demo.service;

import com.example.demo.dto.ViewDto;
import com.example.demo.entity.DailyViews;
import com.example.demo.repository.DailyViewsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class ViewService {
    private static final ZoneId ZONE = ZoneId.of("Asia/Kolkata");
    private final DailyViewsRepository repo;

    public ViewService(DailyViewsRepository repo) { this.repo = repo; }

    public ViewDto incrementAndGetView() {
        repo.increment(LocalDate.now(ZONE));
        return getView();
    }

    public ViewDto getView() {
        long today = repo.findById(LocalDate.now(ZONE)).map(DailyViews::getViewCount).orElse(0L);
        return new ViewDto(repo.totalViews(), today);
    }
}