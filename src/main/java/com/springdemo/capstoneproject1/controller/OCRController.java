package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.client.AIOCRClient;
import com.springdemo.capstoneproject1.dto.OCRResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
@RequiredArgsConstructor
// OCR 추출용 Controller
public class OCRController {

    private final AIOCRClient aiOcrClient;

    @PostMapping("/extract")
    public OCRResponse extract(@RequestParam("file") MultipartFile file) {
        return aiOcrClient.ocr(file);
    }
}
