package com.microservices_project_fitness.aisevice.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices_project_fitness.aisevice.model.Activity;
import com.microservices_project_fitness.aisevice.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GeminiService geminiService;

//    public String generateRecommendation(Activity activity) {
//        String prompt = createPromptForActivity(activity);
//        String aiResponse = geminiService.getAnswer(prompt);
//        log.info("AI response: {}", aiResponse);
//        processAiResponse(activity, aiResponse);
//        return aiResponse;
//    }

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("AI response: {}", aiResponse);
        return processAiResponse(activity, aiResponse);
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates").get(0).path("content")
                    .path("parts").get(0).path("text");

            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n```", "")
                    .trim();

            log.info("PARSED AI response: {}", jsonContent);

            // Parse the cleaned JSON content
            JsonNode recommendationsNode = mapper.readTree(jsonContent);

            // Build the Recommendation object
            Recommendation recommendation = Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activityType(activity.getActivityType().toString())
                    .createdAt(LocalDateTime.now())
                    .build();

            // Map all the fields from the AI response
            if (recommendationsNode.has("suggestions")) {
                recommendation.setSuggestions(mapper.convertValue(
                        recommendationsNode.get("suggestions"),
                        new TypeReference<List<String>>() {}));
            }

            if (recommendationsNode.has("safetyMeasures")) {
                recommendation.setSafetyMeasures(mapper.convertValue(
                        recommendationsNode.get("safetyMeasures"),
                        new TypeReference<List<String>>() {}));
            }

            if (recommendationsNode.has("improvements")) {
                recommendation.setImprovements(mapper.convertValue(
                        recommendationsNode.get("improvements"),
                        new TypeReference<List<String>>() {}));
            }

            if (recommendationsNode.has("moreRecommendations")) {
                recommendation.setMoreRecommendations(mapper.convertValue(
                        recommendationsNode.get("moreRecommendations"),
                        new TypeReference<List<String>>() {}));
            }

            // Handle nested dietPlan fields
            if (recommendationsNode.has("dietPlan")) {
                JsonNode dietPlanNode = recommendationsNode.get("dietPlan");

                if (dietPlanNode.has("postWorkout")) {
                    recommendation.setPostWorkout(mapper.convertValue(
                            dietPlanNode.get("postWorkout"),
                            new TypeReference<List<String>>() {}));
                }

                if (dietPlanNode.has("hydration")) {
                    recommendation.setHydration(mapper.convertValue(
                            dietPlanNode.get("hydration"),
                            new TypeReference<List<String>>() {}));
                }

                if (dietPlanNode.has("supplements")) {
                    recommendation.setSupplements(mapper.convertValue(
                            dietPlanNode.get("supplements"),
                            new TypeReference<List<String>>() {}));
                }

                // Note: preWorkout is in dietPlan but not in Recommendation model
                // You might want to add it to the model or ignore it
            }

            return recommendation;

        } catch (Exception e) {
            log.error("Error processing AI response", e);
            throw new RuntimeException("Failed to process AI response", e);
        }
    }
//    private void processAiResponse( Activity activity, String aiResponse) {
//        try{
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode rootNode = mapper.readTree(aiResponse);
//            JsonNode textNode = rootNode.path("candidates").get(0).path("content")
//                    .path("parts").get(0).path("text");
//
//            String jsonContent = textNode.asText()
//                    .replaceAll("```json\\n", "")
//                    .replaceAll("\\n```", "")
//                    .trim();
//
//            log.info(" PARSED AI response: {}", jsonContent);
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }



    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyze the following user activity data and provide detailed recommendations in JSON format.
        
        Activity Data:
        {
            "id": "%s",
            "userId": "%s",
            "activityType": "%s",
            "duration": %d,
            "caloriesBurned": %d,
            "startTime": "%s",
            "additionalMatrices": %s,
            "createdAt": "%s",
            "updatedAt": "%s"
        }
        
        Your response must strictly be in JSON format with the following structure:
        {
          "suggestions": [list of personalized suggestions to improve the activity],
          "safetyMeasures": [list of safety tips relevant to the activity],
          "improvements": [areas where the user can improve],
          "dietPlan": {
              "preWorkout": [recommended food/snacks before workout],
              "postWorkout": [recommended food/snacks after workout],
              "hydration": [hydration tips during and after workout],
              "supplements": [optional supplements if needed]
          },
          "moreRecommendations": [additional activities or routines to complement the current one]
        }
        
        Return only the JSON object without any extra explanation or text.
        """,
                activity.getId(),
                activity.getUserId(),
                activity.getActivityType().toString(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getStartTime(),
                activity.getAdditionalMatrices().toString(),
                activity.getCreatedAt(),
                activity.getUpdatedAt()
        );
    }







}
