package com.example.VideoToSummaryGenerator.service;

import com.example.VideoToSummaryGenerator.entity.Artifact;
import com.example.VideoToSummaryGenerator.entity.ArtifactType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.VideoToSummaryGenerator.entity.VideoAsset;
import com.example.VideoToSummaryGenerator.repository.ArtifactRepository;
import com.example.VideoToSummaryGenerator.repository.JobRepository;
import com.example.VideoToSummaryGenerator.repository.VideoAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ScreenShotService {
    private final ArtifactRepository artifactRepo;


    public void generate(VideoAsset video) throws IOException {
        Path screenshotsDir = Paths.get(video.getStoragePath(), "screenshots");
        Files.createDirectories(screenshotsDir);

        // Create dummy screenshot file
        Path screenshotPath = screenshotsDir.resolve("screenshot_1.png");
        Files.write(screenshotPath, "Dummy screenshot content".getBytes());

        Artifact screenshotArtifact = Artifact.builder()
                .video(video)
                .type(ArtifactType.SCREENSHOT)
                .storagePath(screenshotPath.toString())
                .build();

        artifactRepo.save(screenshotArtifact);
    }

}
