package com.springdemo.capstoneproject1.Repository;

import com.springdemo.capstoneproject1.model.*;
import com.springdemo.capstoneproject1.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 PG로 테스트할 때
class MatchRepositoryTest {

    @Autowired LectureRepository lectureRepository;
    @Autowired ChapterRepository chapterRepository;
    @Autowired TranscriptRepository transcriptRepository;
    @Autowired ProblemRepository problemRepository;
    @Autowired MatchRepository matchRepository;

    @Test
    void testSaveAndFind() {
        // 1) FK 부모 엔티티들 선행 저장
        Lecture lecture = new Lecture();
        lecture.setUrl("https://example.com/lec");
        lecture.setDuration(1000.0);
        lecture = lectureRepository.save(lecture);

        Chapter chapter = new Chapter();
        chapter.setTitle("삼각형의 성질");
        chapter.setBookName("고등수학(상)");
        chapter = chapterRepository.save(chapter);

        Transcript ts = new Transcript();
        ts.setLecture(lecture);     // NOT NULL
        ts.setChapter(chapter);     // NOT NULL
        ts.setContent("내심은 각의 이등분선의 교점입니다.");
        ts = transcriptRepository.save(ts);

        Problem pb = new Problem();
        pb.setContent("내심의 정의를 고르시오.");
        pb = problemRepository.save(pb);

        // 2) 이제 Match에 FK 연결해서 저장
        Match m = new Match();
        m.setSimilarity(new BigDecimal("0.9876"));
        m.setChapter(chapter);
        m.setTranscript(ts);
        m.setProblem(pb);

        Match saved = matchRepository.save(m);

        // 3) 검증
        Optional<Match> found = matchRepository.findById(saved.getMatchId());
        assertThat(found).isPresent();
        assertThat(found.get().getSimilarity()).isEqualTo(new BigDecimal("0.9876"));
        assertThat(found.get().getChapter().getChapterId()).isEqualTo(chapter.getChapterId());
        assertThat(found.get().getTranscript().getTranscriptId()).isEqualTo(ts.getTranscriptId());
        assertThat(found.get().getProblem().getProblemId()).isEqualTo(pb.getProblemId());
    }
}
