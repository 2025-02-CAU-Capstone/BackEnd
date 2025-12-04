package com.springdemo.capstoneproject1.client;

import com.springdemo.capstoneproject1.dto.OCRResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AIOCRClient {

    private final WebClient aiWebClient;

    public OCRResponse ocr(MultipartFile image) {
        try {
            // 1. 파일 이름 안전하게 가져오기 (없으면 "temp_file" 사용)
            String filename = image.getOriginalFilename();
            if (filename == null || filename.isEmpty()) {
                filename = "temp_file";
            }

            // 2. Content-Type 안전하게 가져오기 (없으면 기본값 설정)
            MediaType mediaType;
            String contentType = image.getContentType();

            if (contentType != null && !contentType.isEmpty()) {
                try {
                    mediaType = MediaType.parseMediaType(contentType);
                } catch (Exception e) {
                    // 이상한 문자열이 들어왔을 경우 안전한 기본값 사용
                    mediaType = MediaType.APPLICATION_OCTET_STREAM;
                }
            } else {
                // null인 경우 안전한 기본값 사용
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
            }

            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder
                    .part("file", image.getResource())
                    .filename(filename)
                    .contentType(mediaType); // ✅ 이제 안전합니다!

            return aiWebClient.post()
                    .uri("/ocr")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(OCRResponse.class)
                    .block();

        } catch (Exception e) {
            throw new RuntimeException("FastAPI OCR 요청 실패: " + e.getMessage(), e);
        }
    }

}
