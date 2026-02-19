package com.example.VideoToSummaryGenerator.mappers;

import com.example.VideoToSummaryGenerator.dto.VideoResponseDto;
import com.example.VideoToSummaryGenerator.entity.VideoAsset;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public static VideoResponseDto toDto(VideoAsset video) {

        return VideoResponseDto.builder()
                .id(video.getId())
                .filename(video.getFilename())
                .status(video.getStatus().name())
                .createdAt(video.getCreatedAt())
                .build();
    }
}
