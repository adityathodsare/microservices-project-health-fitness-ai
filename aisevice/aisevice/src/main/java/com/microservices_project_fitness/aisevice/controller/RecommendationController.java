package com.microservices_project_fitness.aisevice.controller;


import com.microservices_project_fitness.aisevice.model.Recommendation;
import com.microservices_project_fitness.aisevice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/recommendation")
//public class RecommendationController {
//
//    private final RecommendationService recommendationService;
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable String userId) {
//        return ResponseEntity.ok(recommendationService.getUserRecommendation(userId));
//    }
//
//    @GetMapping("/activity/{activityId}")
//    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityId) {
//        return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
//    }
//
//
//
//}

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendations(@PathVariable String userId) {
        List<Recommendation> recommendations = recommendationService.getUserRecommendation(userId);
        if (recommendations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityId) {
        return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
    }

    // Optional: Add endpoint to check if recommendation exists for an activity
    @GetMapping("/activity/{activityId}/exists")
    public ResponseEntity<Boolean> recommendationExists(@PathVariable String activityId) {
        try {
            recommendationService.getActivityRecommendation(activityId);
            return ResponseEntity.ok(true);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(false);
        }
    }
}
