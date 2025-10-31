package com.springdemo.capstoneproject1.repository;

import com.springdemo.capstoneproject1.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {
}
