package com.springdemo.capstoneproject1.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LectureDTO {
    private Integer lectureId;
    private String url;
    private Double duration;
    private LocalDateTime createdAt;
}