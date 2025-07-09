package com.microservices_project_fitness.healthcare_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SymptomResponseDto {
    private String id;
    private String userId;
    private List<String> symptoms;
    private int age;
    private double weight;
    private String additionalNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}