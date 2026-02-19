package com.example.VideoToSummaryGenerator.service;
import com.example.VideoToSummaryGenerator.entity.Artifact;
import com.example.VideoToSummaryGenerator.entity.ArtifactType;
import com.example.VideoToSummaryGenerator.entity.VideoAsset;
import com.example.VideoToSummaryGenerator.repository.ArtifactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ClipService {

    private final ArtifactRepository artifactRepo;

    public void generate(VideoAsset video) throws IOException {

        // Create clips directory
        Path clipsDir = Paths.get(video.getStoragePath(), "clips");
        Files.createDirectories(clipsDir);

        // Create dummy clip file
        Path clipPath = clipsDir.resolve("clip_1.mp4");
        Files.write(clipPath, "Dummy clip content".getBytes());

        Artifact clipArtifact = Artifact.builder()
                .video(video)
                .type(ArtifactType.CLIP)
                .storagePath(clipPath.toString())
                .build();

        artifactRepo.save(clipArtifact);

        // Create screenshots directory


       
    }
}
