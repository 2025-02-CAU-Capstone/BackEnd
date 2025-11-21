package com.springdemo.capstoneproject1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chapterId;

    private String title;

    private Integer orderIndex;

    private String url;

    private Double duration;

    private LocalDateTime createdAt = LocalDateTime.now();

    // FK: Lecture
    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    // Chapter : Transcript = 1:N
    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    private List<Transcript> transcripts;
}
