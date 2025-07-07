package com.microservices_project_fitness.aisevice.service;


import com.microservices_project_fitness.aisevice.model.Recommendation;
import com.microservices_project_fitness.aisevice.repository.RecommendationRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepositry recommendationRepository;


//    public List<Recommendation> getUserRecommendation(String userId) {
//        return recommendationRepository.findByUserId(userId);
//    }
//
//    public Recommendation getActivityRecommendation(String activityId) {
//        return recommendationRepository.findByActivityId(activityId)
//                .orElseThrow(()-> new RuntimeException("Recommendation not found for activityId : "+ activityId));
//    }


    public Recommendation saveRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("Recommendation not found for activityId: " + activityId));
    }
}
