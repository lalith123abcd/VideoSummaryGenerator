package com.example.VideoToSummaryGenerator.service;

import com.example.VideoToSummaryGenerator.entity.Artifact;
import com.example.VideoToSummaryGenerator.entity.ArtifactType;
import com.example.VideoToSummaryGenerator.entity.VideoAsset;
import com.example.VideoToSummaryGenerator.repository.ArtifactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryService {

    private final ArtifactRepository artifactRepo;

    public void generate(VideoAsset video) throws IOException {

        Path summaryPath = Paths.get(video.getStoragePath(), "Summary.md");
        if (artifactRepo.existsByVideoAndType(video, ArtifactType.SUMMARY)) {
            log.info("Summary already exists for video {}", video.getId());
            return;
        }
        String summaryContent = """
        # Video Summary

        ## High Level Summary
        This video explains backend architecture.

        ## Key Highlights
        - 00:00:10 → Introduction
        - 00:05:20 → Async processing explained
        """;

        Files.write(summaryPath, summaryContent.getBytes());

        Artifact artifact = Artifact.builder()
                .video(video)
                .type(ArtifactType.SUMMARY)
                .storagePath(summaryPath.toString())
                .build();

        artifactRepo.save(artifact);
    }
}
