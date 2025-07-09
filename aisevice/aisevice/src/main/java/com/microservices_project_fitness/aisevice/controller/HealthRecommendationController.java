package com.microservices_project_fitness.aisevice.controller;

import com.microservices_project_fitness.aisevice.model.HealthRecommendation;
import com.microservices_project_fitness.aisevice.repository.HealthRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-recommendations")
@RequiredArgsConstructor
public class HealthRecommendationController {
    private final HealthRecommendationRepository repository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HealthRecommendation>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(repository.findByUserId(userId));
    }

    @GetMapping("/analysis/{analysisId}")
    public ResponseEntity<List<HealthRecommendation>> getByAnalysis(@PathVariable String analysisId) {
        return ResponseEntity.ok(repository.findByAnalysisId(analysisId));
    }
}