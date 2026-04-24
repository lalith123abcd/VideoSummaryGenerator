package com.example.VideoToSummaryGenerator.service;

import com.example.VideoToSummaryGenerator.config.security.SecurityUtil;
import com.example.VideoToSummaryGenerator.dto.ArtifactResponseDto;
import com.example.VideoToSummaryGenerator.dto.HighlightResponseDto;
import com.example.VideoToSummaryGenerator.exception.ResourceNotFoundException;
import com.example.VideoToSummaryGenerator.mappers.ArtifactMapper;
import com.example.VideoToSummaryGenerator.mappers.HighlightMapper;
import com.example.VideoToSummaryGenerator.repository.*;

import com.example.VideoToSummaryGenerator.dto.VideoResponseDto;
import com.example.VideoToSummaryGenerator.entity.*;
import com.example.VideoToSummaryGenerator.mappers.VideoMapper;
import com.example.VideoToSummaryGenerator.repository.ArtifactRepository;
import com.example.VideoToSummaryGenerator.repository.HighlightRepository;
import com.example.VideoToSummaryGenerator.repository.VideoAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.module.ResolutionException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class VideoService {
    private final UserRepository userRepository;
    private final VideoAssetRepository videoRepo;
    private final JobRepository jobRepository;
    private final ArtifactRepository artifactRepo;
    private final HighlightRepository highlightRepo;
    private final SecurityUtil securityUtil;
    private final VideoMapper videoMapper;
    private final ArtifactMapper artifactMapper;
    private final HighlightMapper highlightMapper;

    public String uploadVideo(MultipartFile file) throws IOException {
        Long timeStamp=System.currentTimeMillis();
        Long userId=securityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        String folderName="video_"+timeStamp;
        Path folderPath= Paths.get("../storage/"+folderName);
        Files.createDirectories(folderPath);

        String cleanFileName = Paths.get(file.getOriginalFilename())
                .getFileName()
                .toString();


        Path filePath=folderPath.resolve(cleanFileName);
        Files.write(filePath,file.getBytes());

        // Save video metadata
        VideoAsset videoAsset=VideoAsset.builder()
                .filename(cleanFileName)
                .user(user)

                .storagePath(folderPath.toString())
                .status(VideoStatus.UPLOADED)
                .build();

        videoRepo.save(videoAsset);
        Job job=Job.builder()
                .video(videoAsset)
                .status(JobStatus.QUEUED)
                .retryCount(0)
                .build();
        jobRepository.save(job);

        return "Video uploaded successfully. Job ID: " + job.getId();

    }


    // 🔹 Get all videos
    public List<VideoResponseDto> getAllVideos() {
        return videoRepo.findAll()
                .stream()
                .map(VideoMapper::toDto)
                .toList();
    }

    // 🔹 Get single video
    public VideoResponseDto getVideoById(Long id,Long userId) {

        VideoAsset video = videoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));
        System.out.println("VIDEO USER: " + video.getUser());
        System.out.println("VIDEO USER ID: " + (video.getUser() != null ? video.getUser().getId() : "NULL"));
        System.out.println("REQUEST USER ID: " + userId);
        if(!video.getUser().getId().equals(userId)) throw new RuntimeException("Unauthorized access");
        return videoMapper.toDto(video);
    }

    // 🔹 Get artifacts of a video
    public List<ArtifactResponseDto> getArtifacts(Long videoId,Long userId) {

        // Optional: validate video exists
        VideoAsset video = videoRepo.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + videoId));

        if (!video.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        return artifactRepo.findByVideoId(videoId)
                .stream()
                .map(ArtifactMapper::toDTO)
                .toList();
    }


    // 🔹 Get highlights of a video
    public List<HighlightResponseDto> getHighlights(Long videoId,Long userId) {

        VideoAsset video = videoRepo.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found"));

        // 🔥 IDOR FIX
        if (!video.getUser().getId() .equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }


        return highlightRepo.findByVideoId(videoId)
                .stream()
                .map(HighlightMapper::toDTO)
                .toList();
    }
}
