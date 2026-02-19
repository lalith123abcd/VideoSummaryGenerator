package com.example.VideoToSummaryGenerator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HighlightResponseDto {

    private Long id;
    private Double startTimeSeconds;
    private Double endTimeSeconds;
    private String description;
}
