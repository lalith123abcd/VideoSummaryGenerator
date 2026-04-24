package com.example.VideoToSummaryGenerator.controller;

import com.example.VideoToSummaryGenerator.config.security.SecurityUtil;
import com.example.VideoToSummaryGenerator.dto.ArtifactResponseDto;
import com.example.VideoToSummaryGenerator.dto.HighlightResponseDto;
import com.example.VideoToSummaryGenerator.dto.VideoResponseDto;
import com.example.VideoToSummaryGenerator.entity.Artifact;
import com.example.VideoToSummaryGenerator.entity.Highlight;
import com.example.VideoToSummaryGenerator.entity.User;
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
    private final SecurityUtil securityUtil;





    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {

        // Size limit (10MB)
        if (file.getSize() > 10_000_000) {
            throw new RuntimeException("File too large");
        }

        // Type check
        String type = file.getContentType();
        if (type == null || !type.equals("video/mp4")) {
            throw new RuntimeException("Only MP4 allowed");
        }

        return ResponseEntity.ok(videoService.uploadVideo(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDto> getVideo(@PathVariable Long id) {
        Long userId = securityUtil.getCurrentUserId();
        return ResponseEntity.ok(videoService.getVideoById(id,userId));
    }

    // GET /videos/{id}/artifacts
    @GetMapping("/{id}/artifacts")
    public ResponseEntity<List<ArtifactResponseDto>> getArtifacts(@PathVariable Long id) {
        Long userId = securityUtil.getCurrentUserId();
        return ResponseEntity.ok(videoService.getArtifacts(id,userId));
    }

    // GET /videos/{id}/highlights
    @GetMapping("/{id}/highlights")
    public ResponseEntity<List<HighlightResponseDto>> getHighlights(@PathVariable Long id) {
        Long userId = securityUtil.getCurrentUserId();
        return ResponseEntity.ok(videoService.getHighlights(id,userId));
    }


}
