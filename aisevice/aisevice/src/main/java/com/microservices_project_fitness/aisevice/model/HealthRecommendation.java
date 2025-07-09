package com.microservices_project_fitness.aisevice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "health_recommendations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthRecommendation {
    @Id
    private String id;
    private String analysisId;
    private String userId;
    private List<String> possibleConditions;
    private List<String> recommendedActions;
    private List<String> dietarySuggestions;
    private List<String> lifestyleChanges;
    private List<String> preferredMedicines;
    private List<String> whenToSeeDoctor;
    private LocalDateTime createdAt;
}