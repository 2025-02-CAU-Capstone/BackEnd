package com.springdemo.capstoneproject1.dto;

import lombok.Data;

@Data
public class ProblemCreateRequest {
    private String imageUrl;   // optional
    private String content;    // required (OCR text or typed text)
}
