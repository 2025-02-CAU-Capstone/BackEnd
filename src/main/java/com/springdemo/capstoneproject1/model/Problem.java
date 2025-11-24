package com.springdemo.capstoneproject1.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "problem")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer problemId;

    @Column(length = 500)
    private String imageUrl;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // @OneToOne(mappedBy = "problem", cascade = CascadeType.ALL)
    // private Match match;
}

