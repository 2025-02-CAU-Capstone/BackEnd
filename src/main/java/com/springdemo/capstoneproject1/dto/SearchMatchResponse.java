package com.springdemo.capstoneproject1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SearchMatchResponse {
    private Integer lectureId;
    private Integer chapterId;
    private String startTimestamp;
    private String sentence;
    private String youtubeUrl;
    private LocalDateTime createdAt;
}
