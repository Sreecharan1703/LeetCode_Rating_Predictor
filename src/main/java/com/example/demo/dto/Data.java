package com.example.demo.dto;

import java.util.List;

public class Data {
    private String Id;
    private String Title;
    private String Difficulty;
    private String Rating;
    private String AcceptanceRate;
    private String Likes;
    private String Dislikes;
    private String TotalAccepted;
    private String TotalSubmissions;
    private List<String> Tags;

    public String getId() { return Id; }
    public void setId(String id) { Id = id; }

    public String getTitle() { return Title; }
    public void setTitle(String title) { Title = title; }

    public String getDifficulty() { return Difficulty; }
    public void setDifficulty(String difficulty) { Difficulty = difficulty; }

    public String getRating() { return Rating; }
    public void setRating(String rating) { Rating = rating; }

    public String getAcceptanceRate() { return AcceptanceRate; }
    public void setAcceptanceRate(String acceptanceRate) { AcceptanceRate = acceptanceRate; }

    public String getLikes() { return Likes; }
    public void setLikes(String likes) { Likes = likes; }

    public String getDislikes() { return Dislikes; }
    public void setDislikes(String dislikes) { Dislikes = dislikes; }

    public String getTotalAccepted() { return TotalAccepted; }
    public void setTotalAccepted(String totalAccepted) { TotalAccepted = totalAccepted; }

    public String getTotalSubmissions() { return TotalSubmissions; }
    public void setTotalSubmissions(String totalSubmissions) { TotalSubmissions = totalSubmissions; }

    public List<String> getTags() { return Tags; }
    public void setTags(List<String> tags) { Tags = tags; }
}
