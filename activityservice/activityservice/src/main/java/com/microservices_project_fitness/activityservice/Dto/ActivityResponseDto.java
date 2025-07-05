package com.microservices_project_fitness.activityservice.Dto;


import com.microservices_project_fitness.activityservice.model.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityResponseDto {

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
