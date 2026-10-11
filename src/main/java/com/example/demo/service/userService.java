package com.example.demo.service;

import com.example.demo.dto.UserData;
import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class userService {
    private final QuestionRepo questionRepo;

    public userService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public UserData getUser(String userId,Pageable pageable){
        Map<String,Object> mp = LeetcodeScraperService.getUserContestRating(userId);
        if(mp.isEmpty()){
            UserData userData = new UserData();
            userData.setUserId(userId);
            userData.setAttendedContestsCount(String.valueOf(0));
            userData.setUserRating(null);
            userData.setGlobalRanking(null);
            userData.setTopPercentage(null);
            userData.setTotalParticipants(null);
            userData.setRecommendations(null);
            return userData;
        }

        String userRating = mp.get("rating").toString();

        UserData userData = new UserData();
        userData.setUserId(userId);
        userData.setUserRating(userRating);
        userData.setGlobalRanking(String.valueOf(mp.get("globalRanking")));
        userData.setTopPercentage(String.valueOf(mp.get("topPercentage")));
        userData.setTotalParticipants(String.valueOf(mp.get("totalParticipants")));
        userData.setAttendedContestsCount(String.valueOf(mp.get("attendedContestsCount")));

        int userRatingInt = (int) Double.parseDouble(userRating);
        List<Question> recommendations = getQuestionsInRange(userRatingInt-50,userRatingInt+200,pageable);
        userData.setRecommendations(recommendations);

        return userData;
    }

    public List<Question> getQuestionsInRange(int minRange, int maxRange, Pageable pageable) {
        return questionRepo.findByRatingBetween(minRange,maxRange,pageable);
    }
}
