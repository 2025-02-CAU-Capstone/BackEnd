package com.springdemo.capstoneproject1.repository;

import com.springdemo.capstoneproject1.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Integer> {
}
