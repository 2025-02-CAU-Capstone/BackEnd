package com.springdemo.capstoneproject1.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChapterDTO {
    private Integer chapterId;
    private String title;
    private String bookName;
    private Integer startPage;
    private LocalDateTime createdAt;
}
