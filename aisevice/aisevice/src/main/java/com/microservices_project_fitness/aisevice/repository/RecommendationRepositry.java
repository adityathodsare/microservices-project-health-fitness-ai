package com.microservices_project_fitness.aisevice.repository;


import com.microservices_project_fitness.aisevice.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepositry extends MongoRepository<Recommendation, String> {

    List<Recommendation> findByUserId(String userId);

    List<Recommendation> findByActivityId(String activityId);

    // repo


}

