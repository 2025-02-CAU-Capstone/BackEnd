package com.springdemo.capstoneproject1.service;

import com.springdemo.capstoneproject1.model.Chapter;
import com.springdemo.capstoneproject1.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;

    public List<Chapter> findAll() {
        return chapterRepository.findAll();
    }

    public Optional<Chapter> findById(Integer id) {
        return chapterRepository.findById(id);
    }

    public Chapter save(Chapter chapter) {
        // createdAt 자동 세팅
        if (chapter.getCreatedAt() == null) {
            chapter.setCreatedAt(java.time.LocalDateTime.now());
        }
        return chapterRepository.save(chapter);
    }

    public void delete(Integer id) {
        chapterRepository.deleteById(id);
    }

    public List<Chapter> findAllOrdered() {
        return chapterRepository.findAllByOrderByOrderIndexAsc();
    }
}
