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
public class TranscriptService {
    private final ArtifactRepository artifactRepo;

    public void generate(VideoAsset video) throws IOException {

        Path transcriptPath = Paths.get(video.getStoragePath(), "transcript.txt");

        Files.write(transcriptPath,
                "Dummy transcript generated.".getBytes());

        Artifact artifact = Artifact.builder()
                .video(video)
                .type(ArtifactType.TRANSCRIPT)
                .storagePath(transcriptPath.toString())
                .build();

        artifactRepo.save(artifact);
    }
}
