package com.microservices_project_fitness.healthcare_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "symptom_records")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SymptomRecord {
    @Id
    private String id;
    private String userId;
    private List<String> symptoms;
    private int age;
    private double weight;
    private String additionalNotes;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}