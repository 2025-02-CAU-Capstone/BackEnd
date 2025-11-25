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
        int seconds = convertToSeconds(timestamp);
        return baseUrl + "?t=" + seconds;
    }

    private int convertToSeconds(String ts) {
        String[] p = ts.split(":");
        return Integer.parseInt(p[0]) * 3600 +
                Integer.parseInt(p[1]) * 60 +
                Integer.parseInt(p[2]);
    }
}

