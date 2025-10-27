package com.springdemo.capstoneproject1.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lecture")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lectureId;

    @Column(length = 500)
    private String url;

    @Column(nullable = false)
    private Double duration;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<Transcript> transcripts = new ArrayList<>();
}