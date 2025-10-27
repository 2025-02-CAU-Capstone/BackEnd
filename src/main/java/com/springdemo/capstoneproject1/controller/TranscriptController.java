package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.model.Transcript;
import com.springdemo.capstoneproject1.service.TranscriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transcripts")
@RequiredArgsConstructor
public class TranscriptController {
    private final TranscriptService transcriptService;

    @GetMapping
    public List<Transcript> getAll() {
        return transcriptService.findAll();
    }

    @GetMapping("/{id}")
    public Transcript getById(@PathVariable Integer id) {
        return transcriptService.findById(id).orElse(null);
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
