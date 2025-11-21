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

    // TXT file upload API
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadTranscript(
            @RequestParam("file") MultipartFile file,
            @RequestParam("lectureId") Integer lectureId,
            @RequestParam("chapterId") Integer chapterId) {

        try {
            transcriptService.processTranscriptFile(file, lectureId, chapterId);
            return ResponseEntity.ok("Transcript uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // JSON upload API
    @PostMapping("/upload-json")
    public ResponseEntity<String> uploadTranscriptJson(@RequestBody TranscriptUploadRequest req) {
        transcriptService.processTranscriptJson(req);
        return ResponseEntity.ok("JSON upload success.");
    }

    // Lecture별로 Transcript 일괄 삭제
    @DeleteMapping("/lecture/{lectureId}")
    public void deleteByLecture(@PathVariable Integer lectureId) {
        transcriptService.deleteByLectureId(lectureId);
    }

//    @GetMapping("/{id}")
//    public TranscriptDTO getById(@PathVariable Integer id) {
//        Transcript transcript = transcriptService.findById(id)
//                .orElseThrow(() -> new RuntimeException("Transcript not found"));
//
//        TranscriptDTO dto = new TranscriptDTO();
//        dto.setTranscriptId(transcript.getTranscriptId());
//        dto.setStartTime(transcript.getStartTime());
//        dto.setContent(transcript.getContent());
//        dto.setCreatedAt(transcript.getCreatedAt());
//        dto.setLectureId(transcript.getLecture().getLectureId());
//
//        return dto;
//    }

    // Basic CRUD operations
    @GetMapping
    public List<TranscriptDTO> getAll() {
        return transcriptService.findAll()
                .stream()
                .map(tr -> {
                    TranscriptDTO dto = new TranscriptDTO();
                    dto.setTranscriptId(tr.getTranscriptId());
                    dto.setStartTime(tr.getStartTime());
                    dto.setContent(tr.getContent());
                    dto.setCreatedAt(tr.getCreatedAt());
                    dto.setLectureId(tr.getLecture().getLectureId());
                    return dto;
                })
                .collect(Collectors.toList());
    }




    @PostMapping
    public Transcript create(@RequestBody Transcript transcript) {
        return transcriptService.save(transcript);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        transcriptService.delete(id);
    }
}
