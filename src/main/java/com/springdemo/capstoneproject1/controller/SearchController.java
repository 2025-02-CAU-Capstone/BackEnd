package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.client.AISearchClient;
import com.springdemo.capstoneproject1.dto.AISearchResponse;
import com.springdemo.capstoneproject1.dto.SearchMatchResponse;
import com.springdemo.capstoneproject1.dto.SearchRequest;
import com.springdemo.capstoneproject1.model.Chapter;
import com.springdemo.capstoneproject1.repository.ChapterRepository;
import com.springdemo.capstoneproject1.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final AISearchClient aiSearchClient;
    private final HistoryService historyService;
    private final ChapterRepository chapterRepository;

    @PostMapping("/match")
    public SearchMatchResponse match(@RequestBody SearchRequest req) {

        // 1) FastAPI 의미기반 검색 호출
        AISearchResponse ai = aiSearchClient.search(req.getSelectedText());

        // 2) YouTube URL 생성
        Chapter chapter = chapterRepository.findById(ai.getChapterId())
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        String youtubeUrl = buildYoutubeUrl(chapter.getUrl(), ai.getStartTimestamp());

        // 3) History 저장
        historyService.saveHistory(req.getSelectedText(), ai);

        // 4) FE 반환
        return new SearchMatchResponse(
                ai.getLectureId(),
                ai.getChapterId(),
                ai.getStartTimestamp(),
                ai.getSentence(),
                youtubeUrl,
                LocalDateTime.now()
        );
    }

    private String buildYoutubeUrl(String baseUrl, String timestamp) {
        double seconds = convertToSeconds(timestamp);

        int finalSeconds = (int) seconds;  // 소수점 제거 (floor)

        return baseUrl + "?t=" + finalSeconds;
    }


    private double convertToSeconds(String timestamp) {

        if (timestamp == null || timestamp.isEmpty()) {
            return 0.0;
        }

        // 입력 예: "12:34:56,789"
        timestamp = timestamp.trim();

        // 1. 콤마(,)를 소수점(.)으로 변경 → "12:34:56.789"
        timestamp = timestamp.replace(",", ".");

        // 2. HH:MM:SS(.ms) 형태인지 체크
        String[] hms = timestamp.split(":");

        double seconds = 0.0;

        if (hms.length == 3) {
            // HH:MM:SS(.ms)
            int hours = Integer.parseInt(hms[0]);
            int minutes = Integer.parseInt(hms[1]);
            double sec = Double.parseDouble(hms[2]); // 밀리초 포함 처리

            seconds = hours * 3600 + minutes * 60 + sec;

        } else if (hms.length == 2) {
            // MM:SS(.ms)
            int minutes = Integer.parseInt(hms[0]);
            double sec = Double.parseDouble(hms[1]);

            seconds = minutes * 60 + sec;

        } else {
            // 그냥 숫자 혹은 SS(.ms)
            seconds = Double.parseDouble(timestamp);
        }

        return seconds;
    }

}

