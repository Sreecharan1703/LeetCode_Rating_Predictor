package com.example.demo.service;

import com.example.demo.dto.Data;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;

@Service
//@EnableCaching
public class MainService {
    LeetcodeScraperService leetcodeScraperService;
    MainService(LeetcodeScraperService leetcodeScraperService) {
        this.leetcodeScraperService = leetcodeScraperService;
    }

    private String convertListtoString(List<String> topiclist){
        if(topiclist.isEmpty()){
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(String topic:topiclist){
            sb.append(topic).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }

    public Data getDetails(int id){
        Map<String,Object> mp = LeetcodeScraperService.getLeetCodeData(id);

        List<String> topiclist = (List<String>) mp.get("TopicTags");
        String topicString = convertListtoString(topiclist);

        Data outputData = new Data();
        outputData.setId((String)mp.get("ID"));
        outputData.setTitle((String)mp.get("Title"));
        outputData.setDifficulty((String)mp.get("Difficulty"));
        outputData.setAcceptanceRate((String)mp.get("AcceptanceRate"));
        outputData.setDislikes(String.valueOf(mp.get("Dislikes")));
        outputData.setLikes(String.valueOf(mp.get("Likes")));
        outputData.setTotalAccepted(String.valueOf(mp.get("TotalAccepted")));
        outputData.setTotalSubmissions(String.valueOf(mp.get("TotalSubmissions")));
        outputData.setTags(topiclist);

        String query = mp.get("ID") + "," + mp.get("Likes") + "," + mp.get("Dislikes") +
                "," + mp.get("Difficulty") + "," + mp.get("AcceptanceRate") +
                "," + mp.get("TotalAccepted") + "," + mp.get("TotalSubmissions") +
                "," + topicString;

        String rating = getRating(query,(String)mp.get("ID"));
        double rating_num = parseDouble(rating);
        int rating_in_int = (int) rating_num;
        outputData.setRating(Integer.toString(rating_in_int));

        return outputData;
    }

//    @Cacheable(value = "Ratings",key = "#id")
    public String getRating(String query,String id){
        return predictValue(query);
    }

    public String predictValue(String query){
        return "1111";
//        try{
//            ProcessBuilder pb = new ProcessBuilder("python3","ml_requirements/mlPredictor.py",query);
//            pb.redirectErrorStream(true);
//            Process p = pb.start();
//
//            String result = new String(p.getInputStream().readAllBytes()).trim();
//
//            int exitcode = p.waitFor();
//            if(exitcode != 0){
//                return "Python Crashed. Log:\n" + result;
//            }
//            return result;
//        }
//        catch(Exception e){
//            throw new RuntimeException(e);
//        }
    }
}
