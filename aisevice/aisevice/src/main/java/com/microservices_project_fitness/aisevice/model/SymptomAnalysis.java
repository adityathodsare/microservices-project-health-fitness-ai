package com.microservices_project_fitness.aisevice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "symptom_analysis")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SymptomAnalysis {
    @Id
    private String id;
    private String userId;
    private List<String> symptoms;
    private int age;
    private double weight;
    private String additionalNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}