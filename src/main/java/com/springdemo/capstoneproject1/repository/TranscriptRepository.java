package com.springdemo.capstoneproject1.repository;

import com.springdemo.capstoneproject1.model.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranscriptRepository extends JpaRepository<Transcript, Integer> {

    // Chapter 기반 Transcript 목록 조회
    List<Transcript> findByChapter_ChapterId(Integer chapterId);

    // Lecture 기준 Transcript 전체 조회 (JOIN 필요)
    List<Transcript> findByChapter_Lecture_LectureId(Integer lectureId);

    void deleteByChapter_ChapterId(Integer chapterId);
}
