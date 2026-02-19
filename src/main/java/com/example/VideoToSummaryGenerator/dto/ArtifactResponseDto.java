package com.example.VideoToSummaryGenerator.dto;

import com.example.VideoToSummaryGenerator.entity.ArtifactType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtifactResponseDto {

    private Long id;
    private ArtifactType type;
    private String storagePath;
}
