package com.springdemo.capstoneproject1.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MatchDTO {
    private Integer matchId;
    private BigDecimal similarity;
    private LocalDateTime createdAt;
    private Integer transcriptId;
    private Integer chapterId;
    private Integer problemId;
}
