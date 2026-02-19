package com.example.VideoToSummaryGenerator.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "jobs")
@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    private Integer retryCount = 0;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private VideoAsset video;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
