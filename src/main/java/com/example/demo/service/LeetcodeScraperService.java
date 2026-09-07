package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeetcodeScraperService {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String USER_AGENT = "Mozilla/5.0";

    public static Map<String, Object> getLeetCodeData(int frontendId) throws Exception {
        String titleSlug = getTitleSlugForId(frontendId);
        if (titleSlug == null) {
            throw new RuntimeException("Dude, LeetCode doesn't have a question with id: " + frontendId);
        }

        return fetchGraphQLData(titleSlug);
    }

    private static String getTitleSlugForId(int frontendId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://leetcode.com/api/problems/all/"))
                .header("User-Agent", USER_AGENT)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch problem list. HTTP " + response.statusCode());
        }

        JsonNode root = mapper.readTree(response.body());
        JsonNode pairs = root.path("stat_status_pairs");

        for (JsonNode pair : pairs) {
            JsonNode stat = pair.path("stat");
            if (stat.path("frontend_question_id").asInt() == frontendId) {
                return stat.path("question__title_slug").asText();
            }
        }
        return null;
    }

    private static Map<String, Object> fetchGraphQLData(String titleSlug) throws Exception {
        String query = """
            query questionData($titleSlug: String!) {
              question(titleSlug: $titleSlug) {
                questionFrontendId
                title
                difficulty
                likes
                dislikes
                topicTags {
                  name
                }
                stats
              }
            }
            """;

        // Construct the GraphQL Payload
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("query", query);
        Map<String, String> variables = new HashMap<>();
        variables.put("titleSlug", titleSlug);
        payloadMap.put("variables", variables);

        String jsonPayload = mapper.writeValueAsString(payloadMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://leetcode.com/graphql"))
                .header("User-Agent", USER_AGENT)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch GraphQL data. HTTP " + response.statusCode());
        }

        JsonNode root = mapper.readTree(response.body());
        JsonNode questionNode = root.path("data").path("question");

        if (questionNode.isMissingNode() || questionNode.isNull()) {
            throw new RuntimeException("Question data not found.");
        }

        String statsString = questionNode.path("stats").asText();
        JsonNode statsNode = mapper.readTree(statsString);

        List<String> tags = new ArrayList<>();
        for (JsonNode tagNode : questionNode.path("topicTags")) {
            tags.add(tagNode.path("name").asText());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("ID", questionNode.path("questionFrontendId").asText());
        result.put("Title", questionNode.path("title").asText());
        result.put("Difficulty", questionNode.path("difficulty").asText());
        result.put("Likes", questionNode.path("likes").asInt());
        result.put("Dislikes", questionNode.path("dislikes").asInt());
        result.put("Topic Tags", tags);
        result.put("Acceptance Rate", statsNode.path("acRate").asText());
        result.put("Total Accepted", statsNode.path("totalAcceptedRaw").asLong());
        result.put("Total Submissions", statsNode.path("totalSubmissionRaw").asLong());

        return result;
    }
}