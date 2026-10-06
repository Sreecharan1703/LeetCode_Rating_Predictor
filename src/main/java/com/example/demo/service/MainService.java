package com.example.demo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@EnableCaching
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

    public void getDetails(int id, Model model) throws Exception{
        Map<String,Object> mp = LeetcodeScraperService.getLeetCodeData(id);

        List<String> topiclist = new ArrayList<>();
        topiclist = (List<String>) mp.get("Topic Tags");

        String  topicString = convertListtoString(topiclist);

        model.addAttribute("Tags",mp.get("Topic Tags"));
        model.addAttribute("Likes",mp.get("Likes"));
        model.addAttribute("Id",mp.get("ID"));
        model.addAttribute("Title",mp.get("Title"));
        model.addAttribute("Dislikes",mp.get("Dislikes"));
        model.addAttribute("Difficulty",mp.get("Difficulty"));
        model.addAttribute("TotalAccepted",mp.get("Total Accepted"));
        model.addAttribute("AcceptanceRate",mp.get("Acceptance Rate"));
        model.addAttribute("TotalSubmissions",mp.get("Total Submissions"));

        String query = mp.get("ID") + "," + mp.get("Likes") + "," + mp.get("Dislikes") +
                "," + mp.get("Difficulty") + "," + mp.get("Acceptance Rate") +
                "," + mp.get("Total Accepted") + "," + mp.get("Total Submissions") +
                "," + topicString;

        String rating = getRating(query,(String)mp.get("ID"));
        double rating_num =  Double.parseDouble(rating);
        model.addAttribute("Rating",(int)rating_num);
    }

    @Cacheable(value = "Ratings",key = "#id")
    public String getRating(String query,String id) throws Exception{
        return predictValue(query);
    }

    public String predictValue(String query) throws Exception{
        try{
            ProcessBuilder pb = new ProcessBuilder("python3","ml_requirements/mlPredictor.py","query");
            pb.redirectErrorStream(true);
            Process p = pb.start();

            String result = new String(p.getInputStream().readAllBytes()).trim();

            int exitcode = p.waitFor();

            if(exitcode != 0){
                BufferedReader errorReader = new BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
                StringBuilder errorMessage = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorMessage.append(line).append("\n");
                }
                return "Python Crashed. Log:\n" + errorMessage.toString();
            }

            return result;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
