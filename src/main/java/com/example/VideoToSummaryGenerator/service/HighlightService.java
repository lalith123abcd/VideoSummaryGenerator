package com.example.VideoToSummaryGenerator.service;


import com.example.VideoToSummaryGenerator.entity.Artifact;
import com.example.VideoToSummaryGenerator.entity.ArtifactType;
import com.example.VideoToSummaryGenerator.entity.Highlight;
import com.example.VideoToSummaryGenerator.entity.VideoAsset;
import com.example.VideoToSummaryGenerator.repository.ArtifactRepository;
import com.example.VideoToSummaryGenerator.repository.HighlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepo;

    public void generate(VideoAsset video) {

        Highlight h1 = Highlight.builder()
                .video(video)
                .startTimeSeconds(10.0)
                .endTimeSeconds(40.0)
                .description("Introduction to backend architecture")
                .build();

        Highlight h2 = Highlight.builder()
                .video(video)
                .startTimeSeconds(120.0)
                .endTimeSeconds(180.0)
                .description("Async job processing explanation")
                .build();

        highlightRepo.save(h1);
        highlightRepo.save(h2);
    }
}
