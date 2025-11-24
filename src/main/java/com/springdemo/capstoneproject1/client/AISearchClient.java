package com.springdemo.capstoneproject1.client;

import com.springdemo.capstoneproject1.dto.AISearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class AISearchClient {

    private final WebClient webClient;

    public AISearchClient(@Value("${ai-server.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public AISearchResponse search(String question) {
        return webClient.post()
                .uri("/search")
                .bodyValue(Map.of("question", question))
                .retrieve()
                .bodyToMono(AISearchResponse.class)
                .block();
    }
}

