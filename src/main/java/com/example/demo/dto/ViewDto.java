package com.example.demo.dto;

public class ViewDto {
    public long totalViews;
    public long todayViews;

    public long getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(long totalViews) {
        this.totalViews = totalViews;
    }

    public long getTodayViews() {
        return todayViews;
    }

    public void setTodayViews(long todayViews) {
        this.todayViews = todayViews;
    }

    public ViewDto(long totalViews, long todayViews) {
        this.totalViews = totalViews;
        this.todayViews = todayViews;
    }
}
