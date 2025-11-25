package com.springdemo.capstoneproject1.dto;

import lombok.Data;

import java.util.List;

@Data
public class OCRResponse {

    private boolean success;

    private List<TextBox> textBoxes;   // 박스 목록
    private List<String> sentences;    // 분석된 문장 리스트

    private String raw_text;           // 전체 텍스트
    private double confidence;         // 전체 OCR 신뢰도

    private int imageWidth;            // 원본 이미지 너비
    private int imageHeight;           // 원본 이미지 높이

    private String message;            // 오류 시 메시지(옵션)
}
