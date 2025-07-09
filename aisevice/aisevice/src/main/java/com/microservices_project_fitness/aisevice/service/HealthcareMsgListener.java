package com.microservices_project_fitness.aisevice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices_project_fitness.aisevice.model.SymptomAnalysis;
import com.microservices_project_fitness.aisevice.model.HealthRecommendation;
import com.microservices_project_fitness.aisevice.repository.HealthRecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HealthcareMsgListener {
    private final HealthAiService healthAiService;
    private final HealthRecommendationRepository healthRecommendationRepository;

    @RabbitListener(queues = "${rabbitmq.healthcare.queue.name}")
    public void processSymptomAnalysis(SymptomAnalysis analysis) {
        try {
            log.info("Processing symptom analysis for user: {}", analysis.getUserId());
            HealthRecommendation recommendation = healthAiService.generateRecommendation(analysis);
            HealthRecommendation saved = healthRecommendationRepository.save(recommendation);
            log.info("Successfully generated recommendation ID: {}", saved.getId());
        } catch (Exception e) {
            log.error("Failed to process symptom analysis: {}", analysis, e);
            // Consider implementing dead-letter queue handling here
        }
    }
}