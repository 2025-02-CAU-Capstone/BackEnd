package com.springdemo.capstoneproject1.service;

import com.springdemo.capstoneproject1.dto.AISearchResponse;
import com.springdemo.capstoneproject1.dto.HistoryResponse;
import com.springdemo.capstoneproject1.model.Chapter;
import com.springdemo.capstoneproject1.model.History;
import com.springdemo.capstoneproject1.repository.ChapterRepository;
import com.springdemo.capstoneproject1.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final ChapterRepository chapterRepository;

    public void saveHistory(String queryText, AISearchResponse ai) {

        // chapter 정보 불러오기
        Chapter chapter = chapterRepository.findById(ai.getChapterId())
                .orElseThrow(() -> new RuntimeException("Chapter not found: " + ai.getChapterId()));

        // chapter.url + timestamp → 유튜브 링크 만들기
        String youtubeUrl = buildYoutubeUrl(chapter.getUrl(), ai.getStartTimestamp());

        History history = History.builder()
                .queryText(queryText)
                .lectureId(ai.getLectureId())
                .chapterId(ai.getChapterId())
                .timestamp(ai.getStartTimestamp())
                .youtubeUrl(youtubeUrl)
                .createdAt(LocalDateTime.now())
                .build();

        historyRepository.save(history);
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

    public List<History> getAllHistory() {
        return historyRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public List<HistoryResponse> getHistoryResponses() {

        List<History> histories = historyRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        return histories.stream().map(h -> {

            // chapter, lecture title 가져오기
            Chapter chapter = chapterRepository.findById(h.getChapterId())
                    .orElseThrow(() -> new RuntimeException("Chapter not found: " + h.getChapterId()));

            String formattedCreatedAt = h.getCreatedAt().toLocalDate()
                    + " " + h.getCreatedAt().toLocalTime().withNano(0);

            return HistoryResponse.builder()
                    .id(h.getId())
                    .queryText(h.getQueryText())

                    .lectureId(h.getLectureId())
                    .lectureTitle(chapter.getLecture().getTitle())

                    .chapterId(h.getChapterId())
                    .chapterTitle(chapter.getTitle())

                    .timestamp(h.getTimestamp())
                    .youtubeUrl(h.getYoutubeUrl())
                    .createdAt(formattedCreatedAt)
                    .build();

        }).toList();
    }
}
