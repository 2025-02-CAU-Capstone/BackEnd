package com.springdemo.capstoneproject1.repository;

import com.springdemo.capstoneproject1.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findAll();
    // Lecture 기준으로 Chapter 목록 조회
    List<Chapter> findByLecture_LectureId(Integer lectureId);
}
