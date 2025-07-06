package com.microservices_project_fitness.aisevice.service;


import com.microservices_project_fitness.aisevice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GeminiService geminiService;

    public String generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("AI response: {}", aiResponse);
        return aiResponse;
    }

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
