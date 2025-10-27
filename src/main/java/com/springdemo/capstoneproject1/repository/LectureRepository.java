package com.springdemo.capstoneproject1.repository;

import com.springdemo.capstoneproject1.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
}
