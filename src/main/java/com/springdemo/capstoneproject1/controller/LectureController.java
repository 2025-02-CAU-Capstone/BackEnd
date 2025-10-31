package com.springdemo.capstoneproject1.controller;

import com.springdemo.capstoneproject1.model.Lecture;
import com.springdemo.capstoneproject1.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    @GetMapping
    public List<Lecture> getAll() {
        return lectureService.findAll();
    }

    @GetMapping("/{id}")
    public Lecture getById(@PathVariable Integer id) {
        return lectureService.findById(id).orElse(null);
    }

    @PostMapping
    public Lecture create(@RequestBody Lecture lecture) {
        return lectureService.save(lecture);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        lectureService.delete(id);
    }
}
