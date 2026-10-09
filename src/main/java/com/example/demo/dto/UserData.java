package com.example.demo.dto;

import com.example.demo.entity.Question;

import java.util.List;

public class UserData {
    private String userId;
    private String attendedContestsCount;
    private String userRating;
    private String globalRanking;
    private String totalParticipants;
    private String topPercentage;
    private List<Question> recommendations;

    public List<Question> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Question> recommendations) {
        this.recommendations = recommendations;
    }

    public String getTopPercentage() {
        return topPercentage;
    }

    public void setTopPercentage(String topPercentage) {
        this.topPercentage = topPercentage;
    }

    public String getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(String totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public String getGlobalRanking() {
        return globalRanking;
    }

    public void setGlobalRanking(String globalRanking) {
        this.globalRanking = globalRanking;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getAttendedContestsCount() {
        return attendedContestsCount;
    }

    public void setAttendedContestsCount(String attendedContestsCount) {
        this.attendedContestsCount = attendedContestsCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
