package com.springdemo.capstoneproject1.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AIOCRClient {

    private final WebClient aiWebClient;

    public String ocr(MultipartFile image) {
        return aiWebClient.post()
                .uri("/ocr")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", image.getResource()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
