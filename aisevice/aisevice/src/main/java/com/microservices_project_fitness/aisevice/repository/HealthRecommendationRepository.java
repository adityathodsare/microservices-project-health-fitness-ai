package com.microservices_project_fitness.aisevice.repository;

import com.microservices_project_fitness.aisevice.model.HealthRecommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface HealthRecommendationRepository extends MongoRepository<HealthRecommendation, String> {
    List<HealthRecommendation> findByUserId(String userId);
    List<HealthRecommendation> findByAnalysisId(String analysisId);
}