package com.springdemo.capstoneproject1.service;

import com.springdemo.capstoneproject1.client.AIOCRClient;
import com.springdemo.capstoneproject1.client.AISearchClient;
import com.springdemo.capstoneproject1.dto.AISearchResponse;
import com.springdemo.capstoneproject1.dto.OCRResponse;
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
    private final HistoryService historyService;

    public ProblemSearchResponse processProblemWithImage(MultipartFile image) {

        // 1) FastAPI OCR 호출 → OCRResponse 받음
        OCRResponse ocrRes = aiOcrClient.ocr(image);

        // 여기서 text는 ocrRes.getSentences() 를 조합하거나
        // FastAPI에서 full_text 필드를 따로 만들었다면 그걸 사용
        String extractedText = String.join(" ", ocrRes.getSentences());

        // 2) 문제 저장
        Problem saved = problemRepository.save(
                Problem.builder()
                        .imageUrl(null)
                        .content(extractedText)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        // 3) AI Search
        AISearchResponse aiRes = aiSearchClient.search(extractedText);

        // 4) 히스토리 저장
        historyService.saveHistory(extractedText, aiRes);

        // 5) FE에 응답
        ProblemSearchResponse res = new ProblemSearchResponse();
        res.setProblemId(saved.getProblemId());
        res.setLectureId(aiRes.getLectureId());
        res.setChapterId(aiRes.getChapterId());
        res.setStartTimestamp(aiRes.getStartTimestamp());
        res.setPeakTimestamp(aiRes.getPeakTimestamp());
        res.setSentence(aiRes.getSentence());

        return res;
    }


}
