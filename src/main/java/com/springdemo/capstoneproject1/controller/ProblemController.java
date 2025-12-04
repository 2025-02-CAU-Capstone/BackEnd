package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.dto.ProblemDTO;
import com.springdemo.capstoneproject1.dto.ProblemSearchResponse;
import com.springdemo.capstoneproject1.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problems")
public class ProblemController {

//    private final ProblemService problemService;
//
//    @PostMapping(value = "/analyze-with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ProblemSearchResponse analyzeWithImage(
//            @RequestPart MultipartFile image
//    ) {
//        return problemService.processProblemWithImage(image);
//    }
}
