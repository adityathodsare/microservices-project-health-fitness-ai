package com.microservices_project_fitness.aisevice.service;


import com.microservices_project_fitness.aisevice.model.Recommendation;
import com.microservices_project_fitness.aisevice.repository.RecommendationRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepositry recommendationRepository;


    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public List<Recommendation> getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId);
    }
}
