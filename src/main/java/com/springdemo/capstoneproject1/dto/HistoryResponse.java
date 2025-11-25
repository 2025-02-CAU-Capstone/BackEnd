package com.springdemo.capstoneproject1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class HistoryResponse {

    private Long id;
    private String queryText;

    private Integer lectureId;
    private String lectureTitle;

    private Integer chapterId;
    private String chapterTitle;

    private String timestamp;
    private String youtubeUrl;

    private String createdAt;  // formatting: yyyy-MM-dd HH:mm
}
