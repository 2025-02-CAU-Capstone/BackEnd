package com.springdemo.capstoneproject1.service;

import com.springdemo.capstoneproject1.dto.LectureDTO;
import com.springdemo.capstoneproject1.model.Lecture;
import com.springdemo.capstoneproject1.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Lecture createLecture(LectureDTO dto) {

        Lecture lecture = new Lecture();
        lecture.setLectureId(dto.getLectureId()); // client 지정
        lecture.setTitle(dto.getTitle());
        lecture.setDescription(dto.getDescription());
        lecture.setCreatedAt(LocalDateTime.now());

        return lectureRepository.save(lecture);
    }
}
