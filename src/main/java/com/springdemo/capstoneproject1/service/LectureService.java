package com.springdemo.capstoneproject1.service;

import com.springdemo.capstoneproject1.model.Lecture;
import com.springdemo.capstoneproject1.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    public List<Lecture> findAll() {
        return lectureRepository.findAll();
    }

    public Optional<Lecture> findById(Integer id) {
        return lectureRepository.findById(id);
    }

    public Lecture save(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public void delete(Integer id) {
        lectureRepository.deleteById(id);
    }
}
