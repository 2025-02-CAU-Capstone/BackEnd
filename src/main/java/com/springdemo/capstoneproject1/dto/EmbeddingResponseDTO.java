package com.springdemo.capstoneproject1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class EmbeddingResponseDTO {
    private List<String> timestamp;
    private List<String> text;
    private List<Integer> lectureId;
    private List<Integer> chapterId;
    private String embeddingsB64;   // base64로 인코딩한 NPY
}
