package com.springdemo.capstoneproject1.service;

import com.springdemo.capstoneproject1.model.Transcript;
import com.springdemo.capstoneproject1.repository.TranscriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranscriptService {
    private final TranscriptRepository transcriptRepository;

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
}
