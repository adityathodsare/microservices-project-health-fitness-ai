package com.microservices_project_fitness.healthcare_service.controller;

import com.microservices_project_fitness.healthcare_service.dto.SymptomRequestDto;
import com.microservices_project_fitness.healthcare_service.dto.SymptomResponseDto;
import com.microservices_project_fitness.healthcare_service.service.HealthcareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthcare")
public class HealthcareController {

    private final HealthcareService healthcareService;

    public HealthcareController(HealthcareService healthcareService) {
        this.healthcareService = healthcareService;
    }

    @PostMapping("/symptoms")
    public ResponseEntity<SymptomResponseDto> recordSymptoms(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody SymptomRequestDto request) {
        request.setUserId(userId);
        return ResponseEntity.ok(healthcareService.recordSymptoms(request));
    }

    @GetMapping("/symptoms/history")
    public ResponseEntity<List<SymptomResponseDto>> getSymptomHistory(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(healthcareService.getUserSymptomHistory(userId));
    }
}