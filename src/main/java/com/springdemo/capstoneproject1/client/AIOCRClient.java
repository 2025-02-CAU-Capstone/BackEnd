package com.springdemo.capstoneproject1.client;

import com.springdemo.capstoneproject1.dto.OCRResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AIOCRClient {

    private final WebClient aiWebClient;

    public OCRResponse ocr(MultipartFile image) {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", image.getResource())
                    .filename(image.getOriginalFilename())
                    .contentType(MediaType.parseMediaType(image.getContentType()));

            return aiWebClient.post()
                    .uri("/ocr")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(builder.build())
                    .retrieve()
                    .bodyToMono(OCRResponse.class)
                    .block();

        } catch (Exception e) {
            throw new RuntimeException("FastAPI OCR 요청 실패: " + e.getMessage(), e);
        }
    }
}
