package com.springdemo.capstoneproject1.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String queryText;

    private Integer lectureId;
    private Integer chapterId;

    private String timestamp;   // "00:12:08"

    private String youtubeUrl;  // 완성된 URL

    private LocalDateTime createdAt;
}
