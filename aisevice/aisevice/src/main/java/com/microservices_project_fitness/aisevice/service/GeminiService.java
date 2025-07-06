package com.microservices_project_fitness.aisevice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Service
public class GeminiService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl ;

    @Value("${gemini.api.key}")
    private  String GeminiApiKey;

    private  final WebClient webclient;

    public GeminiService(WebClient.Builder webclient) {
        this.webclient = webclient.build();
    }

    public String getAnswer(String question) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
                }
        );

        String response = webclient.post()
                .uri(geminiApiUrl + GeminiApiKey)
                .header("content-type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;


    }

}
