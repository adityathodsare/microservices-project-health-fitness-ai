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

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable String userId) {
        return ResponseEntity.ok(recommendationService.getUserRecommendation(userId));
    }

    @GetMapping("/user/{activityId}")
    public ResponseEntity<List<Recommendation>> getActivityRecommendation(@PathVariable String activityId) {
        return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
    }



}
