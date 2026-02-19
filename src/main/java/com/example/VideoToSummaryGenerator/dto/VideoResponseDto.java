package com.example.VideoToSummaryGenerator.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VideoResponseDto {

    private Long id;
    private String filename;
    private String status;
    private LocalDateTime createdAt;
}
