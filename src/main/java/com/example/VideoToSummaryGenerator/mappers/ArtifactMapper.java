package com.example.VideoToSummaryGenerator.mappers;

import com.example.VideoToSummaryGenerator.dto.ArtifactResponseDto;
import com.example.VideoToSummaryGenerator.entity.Artifact;
import org.springframework.stereotype.Component;

@Component
public class ArtifactMapper {
    public static ArtifactResponseDto toDTO(Artifact artifact){

        return ArtifactResponseDto.builder()
                .id(artifact.getId())
                .type(artifact.getType())
                .storagePath(artifact.getStoragePath())
                .build();
    }


}
