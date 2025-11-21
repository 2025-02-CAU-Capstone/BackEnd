package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.dto.LectureCreateRequest;
import com.springdemo.capstoneproject1.dto.LectureDTO;
import com.springdemo.capstoneproject1.model.Lecture;
import com.springdemo.capstoneproject1.repository.LectureRepository;
import com.springdemo.capstoneproject1.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;
    private final LectureRepository lectureRepository;

    @GetMapping
    public List<Lecture> getAll() {
        return lectureService.findAll();
    }

    @GetMapping("/{id}")
    public Lecture getById(@PathVariable Integer id) {
        return lectureService.findById(id).orElse(null);
    }

    @PostMapping("/create")
    public Lecture createLecture(@RequestBody LectureCreateRequest req) {
        Lecture l = new Lecture();
        l.setLectureId(req.getLectureId());
        l.setTitle(req.getTitle());
        l.setDescription(req.getDescription());
        l.setCreatedAt(LocalDateTime.now());
        return lectureRepository.save(l);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        lectureService.delete(id);
    }
}
