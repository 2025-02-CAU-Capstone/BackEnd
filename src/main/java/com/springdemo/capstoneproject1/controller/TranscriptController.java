package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.dto.TranscriptDTO;
import com.springdemo.capstoneproject1.dto.TranscriptUploadRequest;
import com.springdemo.capstoneproject1.model.Transcript;
import com.springdemo.capstoneproject1.service.TranscriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transcripts")
@RequiredArgsConstructor
public class TranscriptController {

    private final TranscriptService transcriptService;

    /** 모든 Transcript 조회 */
    @GetMapping
    public List<TranscriptDTO> getAll() {
        return transcriptService.findAll()
                .stream()
                .map(t -> {
                    TranscriptDTO dto = new TranscriptDTO();
                    dto.setTranscriptId(t.getTranscriptId());
                    dto.setStartTime(t.getStartTime());
                    dto.setContent(t.getContent());
                    dto.setCreatedAt(t.getCreatedAt());
                    dto.setChapterId(t.getChapter().getChapterId());
                    dto.setLectureId(
                            t.getChapter().getLecture().getLectureId()
                    );
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /** 단일 Transcript 조회 */
    @GetMapping("/{id}")
    public TranscriptDTO getById(@PathVariable Integer id) {
        Transcript t = transcriptService.findById(id)
                .orElseThrow(() -> new RuntimeException("Transcript not found"));

        TranscriptDTO dto = new TranscriptDTO();
        dto.setTranscriptId(t.getTranscriptId());
        dto.setStartTime(t.getStartTime());
        dto.setContent(t.getContent());
        dto.setCreatedAt(t.getCreatedAt());
        dto.setChapterId(t.getChapter().getChapterId());
        dto.setLectureId(t.getChapter().getLecture().getLectureId());

        return dto;
    }

    /** TXT 파일 업로드 */
    @PostMapping(value = "/upload/txt", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadTranscript(
            @RequestParam("file") MultipartFile file,
            @RequestParam("chapterId") Integer chapterId
    ) {
        try {
            transcriptService.processTranscriptFile(file, chapterId);
            return ResponseEntity.ok("Transcript uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    /** JSON 업로드 */
    @PostMapping("/upload/json")
    public ResponseEntity<String> uploadTranscriptJson(@RequestBody TranscriptUploadRequest req) {
        transcriptService.processTranscriptJson(req);
        return ResponseEntity.ok("JSON upload success.");
    }

    /** Chapter 단위 Transcript 일괄 삭제 */
    @DeleteMapping("/chapter/{chapterId}")
    public void deleteByChapterId(@PathVariable Integer chapterId) {
        transcriptService.deleteByChapterId(chapterId);
    }
}
