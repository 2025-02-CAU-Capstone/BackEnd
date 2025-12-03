package com.springdemo.capstoneproject1.dto;

import lombok.Data;

@Data
public class ChapterUpdateRequest {
    private String title;
    private String url;
    private Integer orderIndex;
    private Double duration;
}
