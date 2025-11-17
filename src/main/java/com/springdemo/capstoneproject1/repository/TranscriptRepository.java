package com.springdemo.capstoneproject1.repository;

import com.springdemo.capstoneproject1.model.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranscriptRepository extends JpaRepository<Transcript, Integer> {
    // Lecture ID로 transcript 전체 삭제
    void deleteByLecture_LectureId(Integer lectureId);

    // Lecture ID로 transcript 목록 조회 (필요하면)
    List<Transcript> findByLecture_LectureId(Integer lectureId);
}
