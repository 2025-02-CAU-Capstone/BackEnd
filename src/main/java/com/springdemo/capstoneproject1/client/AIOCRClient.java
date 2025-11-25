package com.springdemo.capstoneproject1.client;

import com.springdemo.capstoneproject1.dto.OCRResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AIOCRClient {

    private final WebClient aiWebClient; // baseUrl 설정된 WebClient

    public OCRResponse ocr(MultipartFile image) {

        return aiWebClient.post()
                .uri("/ocr")   // FastAPI OCR 엔드포인트
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters
                        .fromMultipartData("file", image.getResource()))
                .retrieve()
                .bodyToMono(OCRResponse.class)
                .block();   // 동기 호출
    }
}
