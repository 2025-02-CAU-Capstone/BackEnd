package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.model.*;
import com.springdemo.capstoneproject1.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MatchControllerIntegrationTest {

    @Autowired TestRestTemplate restTemplate;

    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    TranscriptRepository transcriptRepository;
    @Autowired
    ProblemRepository problemRepository;

    @Test
    void testCreateAndGetMatch() {
        // 1) 선행 데이터 저장 (FK 준비)
        Lecture lecture = new Lecture();
        lecture.setUrl("https://example.com/lec");
        lecture.setDuration(1234.5);
        lecture = lectureRepository.save(lecture);

        Chapter chapter = new Chapter();
        chapter.setTitle("삼각형의 성질");
        chapter.setBookName("고등수학(상)");
        chapter = chapterRepository.save(chapter);

        Transcript ts = new Transcript();
        ts.setLecture(lecture);        // NOT NULL
        ts.setChapter(chapter);        // NOT NULL
        ts.setContent("내심은 각의 이등분선의 교점입니다");
        ts = transcriptRepository.save(ts);

        Problem pb = new Problem();
        pb.setContent("내심의 정의를 고르시오");
        pb = problemRepository.save(pb);

        // 2) Match JSON (중첩 객체로 id만 넣어도 JPA가 바인딩 가능)
        Map<String, Object> body = new HashMap<>();
        body.put("similarity", new BigDecimal("0.9876"));
        body.put("chapter", Map.of("chapterId", chapter.getChapterId()));
        body.put("transcript", Map.of("transcriptId", ts.getTranscriptId()));
        body.put("problem", Map.of("problemId", pb.getProblemId()));

        ResponseEntity<Match> post = restTemplate.postForEntity("/api/matches", body, Match.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);

        Match saved = post.getBody();
        assertThat(saved).isNotNull();
        assertThat(saved.getSimilarity()).isEqualTo(new BigDecimal("0.9876"));
        assertThat(saved.getChapter().getChapterId()).isEqualTo(chapter.getChapterId());
        assertThat(saved.getTranscript().getTranscriptId()).isEqualTo(ts.getTranscriptId());
        assertThat(saved.getProblem().getProblemId()).isEqualTo(pb.getProblemId());
    }
}
