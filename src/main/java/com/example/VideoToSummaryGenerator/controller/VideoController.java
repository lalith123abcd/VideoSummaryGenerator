package com.example.VideoToSummaryGenerator.controller;

import com.example.VideoToSummaryGenerator.dto.ArtifactResponseDto;
import com.example.VideoToSummaryGenerator.dto.HighlightResponseDto;
import com.example.VideoToSummaryGenerator.dto.VideoResponseDto;
import com.example.VideoToSummaryGenerator.entity.Artifact;
import com.example.VideoToSummaryGenerator.entity.Highlight;
import com.example.VideoToSummaryGenerator.entity.VideoAsset;
import com.example.VideoToSummaryGenerator.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(videoService.uploadVideo(file));
    }

    @GetMapping
    public ResponseEntity<List<VideoResponseDto>> getAllVideos(){
        return ResponseEntity.ok(videoService.getAllVideos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDto> getVideo(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }

    // GET /videos/{id}/artifacts
    @GetMapping("/{id}/artifacts")
    public ResponseEntity<List<ArtifactResponseDto>> getArtifacts(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getArtifacts(id));
    }

    // GET /videos/{id}/highlights
    @GetMapping("/{id}/highlights")
    public ResponseEntity<List<HighlightResponseDto>> getHighlights(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getHighlights(id));
    }


}
