package com.springdemo.capstoneproject1.dto;

import lombok.Data;
import java.util.List;

@Data
public class EmbeddingMetadataRequest {
    private List<String> timestamp;
    private List<String> text;
    private List<Integer> lectureId;
    private List<Integer> chapterId;
}
