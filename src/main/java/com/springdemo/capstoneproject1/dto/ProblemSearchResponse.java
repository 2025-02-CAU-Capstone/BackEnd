package com.springdemo.capstoneproject1.dto;

import lombok.Data;

@Data
public class ProblemSearchResponse {
    private Integer problemId;

    // AI Search 결과
    private String startTimestamp;
    private String peakTimestamp;
    private Integer lectureId;
    private Integer chapterId;
    private String sentence;
}

// FE에 최종적으로 전달되는 DTO