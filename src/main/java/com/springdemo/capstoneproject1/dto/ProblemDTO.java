package com.springdemo.capstoneproject1.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProblemDTO {
    private Integer problemId;
    private String imageUrl;
    private String content;
    private LocalDateTime createdAt;
}
