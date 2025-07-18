package com.microservices_project_fitness.aisevice.model;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "recommendations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Recommendation {

    @Id
    private  String id;
    private String activityId;
    private String userId;
    private String activityType;
    private List<String> moreRecommendations;
    private List<String> postWorkout ;
    private List<String> hydration ;
    private List<String> supplements ;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safetyMeasures;

    @CreatedDate
    private LocalDateTime createdAt;
}
