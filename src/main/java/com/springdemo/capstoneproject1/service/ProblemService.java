package com.springdemo.capstoneproject1.service;

import com.springdemo.capstoneproject1.client.AIOCRClient;
import com.springdemo.capstoneproject1.client.AISearchClient;
import com.springdemo.capstoneproject1.dto.AISearchResponse;
import com.springdemo.capstoneproject1.dto.ProblemSearchResponse;
import com.springdemo.capstoneproject1.model.Problem;
import com.springdemo.capstoneproject1.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final AIOCRClient aiOcrClient;
    private final AISearchClient aiSearchClient;

    public ProblemSearchResponse processProblemWithImage(MultipartFile image) {

        // 1) FastAPI OCR 서버 호출
        String extractedText = aiOcrClient.ocr(image);

        // 2) 문제 저장
        Problem saved = problemRepository.save(
                Problem.builder()
                        .imageUrl(null)     // 지금은 저장하지 않음
                        .content(extractedText)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        // 3) AI Search
        AISearchResponse aiRes = aiSearchClient.search(extractedText);

        // 4) 묶어서 FE에 반환
        ProblemSearchResponse res = new ProblemSearchResponse();
        res.setProblemId(saved.getProblemId());
        res.setStartTimestamp(aiRes.getStartTimestamp());
        res.setPeakTimestamp(aiRes.getPeakTimestamp());
        res.setLectureId(aiRes.getLectureId());
        res.setChapterId(aiRes.getChapterId());
        res.setSentence(aiRes.getSentence());

        return res;
    }
}
