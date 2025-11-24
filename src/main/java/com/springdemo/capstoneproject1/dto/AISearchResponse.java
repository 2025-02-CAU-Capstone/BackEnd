package com.springdemo.capstoneproject1.dto;

import lombok.Data;

@Data
public class AISearchResponse {
    private String startTimestamp;
    private String peakTimestamp;
    private Integer lectureId;
    private Integer chapterId;
    private String sentence;
}

// FastAPI AI 서버에서 받아올 응답 DTO