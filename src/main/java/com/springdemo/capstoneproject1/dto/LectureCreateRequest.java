package com.springdemo.capstoneproject1.dto;

import lombok.Data;

@Data
public class LectureCreateRequest {
    private String title;
    private String description; // optional
}
