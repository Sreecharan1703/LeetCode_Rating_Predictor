package com.example.demo.service;

import com.example.demo.dto.ViewDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ViewService {
    private final AtomicLong totalViews = new AtomicLong(0);
    private final AtomicLong todayViews =new AtomicLong(0);

    private static final ZoneId ZONE = ZoneId.of("Asia/Kolkata");
    private volatile LocalDate currentDay = LocalDate.now(ZONE);

    private void checkChange(){
        LocalDate today = LocalDate.now(ZONE);
        if(!today.equals(currentDay)){
            synchronized (this) {
                if (!today.equals(currentDay)) {
                    todayViews.set(0);
                    currentDay = today;
                }
            }
        }
    }

    public ViewDto incrementAndGetView(){
        checkChange();
        return new ViewDto(totalViews.incrementAndGet(), todayViews.incrementAndGet());
    }
    public ViewDto getView(){
        checkChange();
        return new ViewDto(totalViews.get(), todayViews.get());
    }
}
