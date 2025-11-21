package com.springdemo.capstoneproject1.dto;

import lombok.Data;

import java.util.List;

@Data
public class TranscriptUploadRequest {
    private Integer lectureId;
    private Integer chapterId;
    private List<TranscriptItemDTO> transcripts;
}