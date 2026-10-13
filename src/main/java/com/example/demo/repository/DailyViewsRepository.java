package com.example.demo.repository;

import com.example.demo.entity.DailyViews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface DailyViewsRepository extends JpaRepository<DailyViews, LocalDate> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO daily_views (view_date, view_count) VALUES (:day, 1) " +
            "ON DUPLICATE KEY UPDATE view_count = view_count + 1", nativeQuery = true)
    void increment(@Param("day") LocalDate day);

    @Query("select coalesce(sum(d.viewCount), 0) from DailyViews d")
    long totalViews();
}