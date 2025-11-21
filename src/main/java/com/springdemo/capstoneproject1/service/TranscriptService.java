package com.springdemo.capstoneproject1.service;

import com.springdemo.capstoneproject1.dto.TranscriptItemDTO;
import com.springdemo.capstoneproject1.dto.TranscriptUploadRequest;
import com.springdemo.capstoneproject1.model.Chapter;
import com.springdemo.capstoneproject1.model.Transcript;
import com.springdemo.capstoneproject1.repository.ChapterRepository;
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

            double startTimeSeconds = convertTimeToSeconds(timePart);

            Transcript t = new Transcript();
            t.setStartTime(startTimeSeconds);
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
            Transcript t = new Transcript();
            t.setStartTime(item.getStartTime());
            t.setContent(item.getContent());
            t.setChapter(chapter);
            t.setCreatedAt(LocalDateTime.now());

            transcriptRepository.save(t);
        }
    }
}
