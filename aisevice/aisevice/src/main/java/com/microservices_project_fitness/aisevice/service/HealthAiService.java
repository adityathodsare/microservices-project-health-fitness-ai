package com.microservices_project_fitness.aisevice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices_project_fitness.aisevice.model.HealthRecommendation;
import com.microservices_project_fitness.aisevice.model.SymptomAnalysis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HealthAiService {
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;

    public HealthRecommendation generateRecommendation(SymptomAnalysis analysis) {
        try {
            String prompt = createStructuredPrompt(analysis);
            String aiResponse = geminiService.getAnswer(prompt);
            log.debug("Raw AI response: {}", aiResponse);

            // Parse the Gemini response
            JsonNode rootNode = objectMapper.readTree(aiResponse);

            // Extract the text content from Gemini's response structure
            String textContent = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            // Clean the JSON response (remove markdown if present)
            String jsonContent = textContent.replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            log.debug("Cleaned JSON content: {}", jsonContent);

            // Parse the actual recommendation JSON
            JsonNode recommendationNode = objectMapper.readTree(jsonContent);

            return HealthRecommendation.builder()
                    .analysisId(analysis.getId())
                    .userId(analysis.getUserId())
                    .possibleConditions(parseStringList(recommendationNode, "possibleConditions"))
                    .recommendedActions(parseStringList(recommendationNode, "recommendedActions"))
                    .dietarySuggestions(parseStringList(recommendationNode, "dietarySuggestions"))
                    .preferredMedicines(parseStringList(recommendationNode, "preferredMedicines"))
                    .lifestyleChanges(parseStringList(recommendationNode, "lifestyleChanges"))
                    .whenToSeeDoctor(parseStringList(recommendationNode, "whenToSeeDoctor"))
                    .createdAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            log.error("Failed to generate health recommendation", e);
            // Fallback recommendation if parsing fails
            return createFallbackRecommendation(analysis);
        }
    }

    private List<String> parseStringList(JsonNode node, String fieldName) {
        List<String> result = new ArrayList<>();
        if (node.has(fieldName)) {
            node.get(fieldName).forEach(item -> result.add(item.asText()));
        }
        return result;
    }

    private String createStructuredPrompt(SymptomAnalysis analysis) {
        return String.format("""
            Analyze these symptoms and provide health recommendations in STRICT JSON format:
            {
              "user": {
                "age": %d,
                "weight": %.1f
              },
              "symptoms": %s,
              "notes": "%s"
            }
            
            Respond ONLY with a JSON object containing these fields:
            {
              "possibleConditions": ["condition1", "condition2"],
              "recommendedActions": ["action1", "action2"],
              "dietarySuggestions": ["suggestion1", "suggestion2"],
              "lifestyleChanges": ["change1", "change2"],
              "preferredMedicines": ["medicine1 most preferred by doctor", "medicine2 most preferred by doctor"],
              "whenToSeeDoctor": ["warning1", "warning2"]
            }
            
            Do not include any additional text or explanation.
            """,
                analysis.getAge(),
                analysis.getWeight(),
                analysis.getSymptoms(),
                analysis.getAdditionalNotes()
        );
    }

    private HealthRecommendation createFallbackRecommendation(SymptomAnalysis analysis) {
        return HealthRecommendation.builder()
                .analysisId(analysis.getId())
                .userId(analysis.getUserId())
                .possibleConditions(List.of("Consult a doctor for proper diagnosis"))
                .recommendedActions(List.of("Get rest", "Stay hydrated"))
                .preferredMedicines(List.of("Paracetamol for fever Consult a doctor before taking any medication"))
                .createdAt(LocalDateTime.now())
                .build();
    }
}