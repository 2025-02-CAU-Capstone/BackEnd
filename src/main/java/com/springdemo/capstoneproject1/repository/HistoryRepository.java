package com.springdemo.capstoneproject1.repository;

import com.springdemo.capstoneproject1.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}

