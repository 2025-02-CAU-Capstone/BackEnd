package com.springdemo.capstoneproject1.client;

import com.springdemo.capstoneproject1.dto.AISearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AISearchClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://<FASTAPI_PUBLIC_IP>:8000")  // AI 서버 주소
            .build();

    public AISearchResponse search(String question) {
        return webClient.post()
                .uri("/search")
                .bodyValue(Map.of("question", question))
                .retrieve()
                .bodyToMono(AISearchResponse.class)
                .block();
    }
}
