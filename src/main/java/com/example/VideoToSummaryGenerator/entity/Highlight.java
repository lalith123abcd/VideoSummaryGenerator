package com.example.VideoToSummaryGenerator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "highlights")
@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class Highlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double startTimeSeconds;

    private Double endTimeSeconds;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private VideoAsset video;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
