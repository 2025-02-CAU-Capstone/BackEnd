package com.springdemo.capstoneproject1.dto;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TranscriptDTO {
    private Integer transcriptId;
    private Double startTime;
    private Double endTime;
    private String content;
    private LocalDateTime createdAt;
    private Integer lectureId;
    private Integer chapterId;
}