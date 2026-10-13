package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "daily_views")
public class DailyViews {
    @Id
    @Column(name = "view_date")
    private LocalDate viewDate;

    @Column(name = "view_count", nullable = false)
    private long viewCount;

    protected DailyViews() {}

    public long getViewCount() { return viewCount; }
}