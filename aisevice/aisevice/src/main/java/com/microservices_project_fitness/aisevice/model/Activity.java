package com.microservices_project_fitness.aisevice.model;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity {
    private String id;
    private String userId;
    private ActivityType activityType;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMatrices;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
