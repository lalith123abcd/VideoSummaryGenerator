package com.example.VideoToSummaryGenerator.mappers;

import com.example.VideoToSummaryGenerator.dto.HighlightResponseDto;
import com.example.VideoToSummaryGenerator.entity.Highlight;
import com.example.VideoToSummaryGenerator.repository.HighlightRepository;
import org.springframework.stereotype.Component;

@Component
public class HighlightMapper {
    public static HighlightResponseDto toDTO(Highlight highlight){
        return HighlightResponseDto
                .builder()
                .id(highlight.getId())
                .startTimeSeconds(highlight.getStartTimeSeconds())
                .endTimeSeconds(highlight.getEndTimeSeconds())
                .description(highlight.getDescription()).build();
    }
}
