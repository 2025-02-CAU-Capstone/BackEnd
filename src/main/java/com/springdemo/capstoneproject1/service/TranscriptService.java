package com.springdemo.capstoneproject1.service;

import com.springdemo.capstoneproject1.dto.TranscriptItemDTO;
import com.springdemo.capstoneproject1.dto.TranscriptUploadRequest;
import com.springdemo.capstoneproject1.model.Chapter;
import com.springdemo.capstoneproject1.model.Lecture;
import com.springdemo.capstoneproject1.model.Transcript;
import com.springdemo.capstoneproject1.repository.ChapterRepository;
import com.springdemo.capstoneproject1.repository.LectureRepository;
import com.springdemo.capstoneproject1.repository.TranscriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranscriptService {

    private final TranscriptRepository transcriptRepository;
    private final ChapterRepository chapterRepository;
    private final LectureRepository lectureRepository;

    public List<Transcript> findAll() {
        return transcriptRepository.findAll();
    }

    public Optional<Transcript> findById(Integer id) {
        return transcriptRepository.findById(id);
    }

    public Transcript save(Transcript transcript) {
        return transcriptRepository.save(transcript);
    }

    public void delete(Integer id) {
        transcriptRepository.deleteById(id);
    }


    /** ---------------------------------------------
     *  TXT 파일 업로드 처리
     * --------------------------------------------- */
    public void processTranscriptFile(MultipartFile file, Integer chapterId) throws Exception {

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));

        List<String> lines = new BufferedReader(
                new InputStreamReader(file.getInputStream())
        ).lines().toList();

        for (String line : lines) {

            if (!line.contains("]")) continue;

            String timePart = line.substring(1, line.indexOf("]"));
            String contentPart = line.substring(line.indexOf("]") + 1).trim();

            Transcript t = new Transcript();
            t.setStartTime(timePart);
            t.setContent(contentPart);
            t.setChapter(chapter);
            t.setCreatedAt(LocalDateTime.now());

            transcriptRepository.save(t);
        }
    }


    /** "00:00:02,610" → 2.610 */
    private double convertTimeToSeconds(String t) {
        String[] parts = t.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);

        String[] secMs = parts[2].split(",");
        int s = Integer.parseInt(secMs[0]);
        int ms = Integer.parseInt(secMs[1]);

        return h * 3600 + m * 60 + s + (ms / 1000.0);
    }

    /** ---------------------------------------------
     *  Chapter ID로 Transcript 전체 삭제
     * --------------------------------------------- */
    @Transactional
    public void deleteByChapterId(Integer chapterId) {
        transcriptRepository.deleteByChapter_ChapterId(chapterId);
    }


    /** ---------------------------------------------
     *  JSON 업로드 처리
     * --------------------------------------------- */
    public void processTranscriptJson(TranscriptUploadRequest req) {

        Chapter chapter = chapterRepository.findById(req.getChapterId())
                .orElseThrow(() -> new RuntimeException("Chapter not found"));

        for (TranscriptItemDTO item : req.getTranscripts()) {

            String raw = item.getStartTime(); // "[00:00:02,610]"

            if (raw == null || raw.length() < 2) {
                throw new RuntimeException("Invalid startTime format: " + raw);
            }

            // TXT에서처럼 → 1번 인덱스부터 ']' 위치까지 substring
            String timePart = raw.substring(1, raw.indexOf("]"));
            // "00:00:02,610"

            Transcript t = new Transcript();
            t.setStartTime(timePart);
            t.setContent(item.getContent());
            t.setChapter(chapter);
            t.setCreatedAt(LocalDateTime.now());

            transcriptRepository.save(t);
        }
    }


    // 챕터가 없으면 생성하는 헬퍼 메서드
    private Chapter getOrCreateChapter(Integer lectureId, Integer chapterId) {
        return chapterRepository.findById(chapterId)
                .orElseGet(() -> {

                    Lecture lecture = lectureRepository.findById(lectureId)
                            .orElseThrow(() -> new RuntimeException("Lecture not found"));

                    Chapter newChapter = new Chapter();
                    newChapter.setChapterId(chapterId);        // ID 그대로 사용
                    newChapter.setTitle("Chapter " + chapterId);
                    newChapter.setCreatedAt(LocalDateTime.now());
                    newChapter.setLecture(lecture);

                    return chapterRepository.save(newChapter);
                });
    }

    public List<Transcript> findByChapterId(Integer chapterId) {
        return transcriptRepository.findByChapter_ChapterId(chapterId);
    }

}
