package com.springdemo.capstoneproject1.repository;

import com.springdemo.capstoneproject1.model.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranscriptRepository extends JpaRepository<Transcript, Integer> {
}
