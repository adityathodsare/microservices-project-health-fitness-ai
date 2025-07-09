package com.microservices_project_fitness.healthcare_service.service;

import com.microservices_project_fitness.healthcare_service.dto.SymptomRequestDto;
import com.microservices_project_fitness.healthcare_service.dto.SymptomResponseDto;
import com.microservices_project_fitness.healthcare_service.model.SymptomRecord;
import com.microservices_project_fitness.healthcare_service.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HealthcareService {

    private final SymptomRepository symptomRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.healthcare.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.healthcare.routing.key}")
    private String routingKey;

    public SymptomResponseDto recordSymptoms(SymptomRequestDto request) {
        // Validate user exists
        Boolean isValid = userValidationService.validateUser(request.getUserId());
        if (!isValid) {
            throw new RuntimeException("User not found");
        }

        // Create and save symptom record
        SymptomRecord record = SymptomRecord.builder()
                .userId(request.getUserId())
                .symptoms(request.getSymptoms())
                .age(request.getAge())
                .weight(request.getWeight())
                .additionalNotes(request.getAdditionalNotes())
                .build();

        SymptomRecord savedRecord = symptomRepository.save(record);

        // Push to RabbitMQ
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, savedRecord);
            log.info("Sent symptom data to RabbitMQ for record ID: {}", savedRecord.getId());
        } catch (Exception e) {
            log.error("Error sending to RabbitMQ: {}", e.getMessage());
        }

        return mapToDto(savedRecord);
    }

    public List<SymptomResponseDto> getUserSymptomHistory(String userId) {
        return symptomRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private SymptomResponseDto mapToDto(SymptomRecord record) {
        return SymptomResponseDto.builder()
                .id(record.getId())
                .userId(record.getUserId())
                .symptoms(record.getSymptoms())
                .age(record.getAge())
                .weight(record.getWeight())
                .additionalNotes(record.getAdditionalNotes())
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .build();
    }
}