package com.springdemo.capstoneproject1.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TranscriptDTO {

    private Integer transcriptId;
    private String startTime;
    private String content;
    private LocalDateTime createdAt;

    private Integer chapterId;
    private Integer lectureId;
}
