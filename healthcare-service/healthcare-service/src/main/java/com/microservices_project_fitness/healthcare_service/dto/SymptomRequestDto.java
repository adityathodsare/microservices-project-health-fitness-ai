package com.microservices_project_fitness.healthcare_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class SymptomRequestDto {
    private String userId;
    private List<String> symptoms;
    private int age;
    private double weight;
    private String additionalNotes;
}