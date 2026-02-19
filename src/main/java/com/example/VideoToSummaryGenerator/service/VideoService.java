package com.example.VideoToSummaryGenerator.service;

import com.example.VideoToSummaryGenerator.dto.ArtifactResponseDto;
import com.example.VideoToSummaryGenerator.dto.HighlightResponseDto;
import com.example.VideoToSummaryGenerator.exception.ResourceNotFoundException;
import com.example.VideoToSummaryGenerator.mappers.ArtifactMapper;
import com.example.VideoToSummaryGenerator.mappers.HighlightMapper;
import com.example.VideoToSummaryGenerator.repository.ArtifactRepository;
import com.example.VideoToSummaryGenerator.repository.HighlightRepository;
import com.example.VideoToSummaryGenerator.repository.VideoAssetRepository;

import com.example.VideoToSummaryGenerator.dto.VideoResponseDto;
import com.example.VideoToSummaryGenerator.entity.*;
import com.example.VideoToSummaryGenerator.mappers.VideoMapper;
import com.example.VideoToSummaryGenerator.repository.ArtifactRepository;
import com.example.VideoToSummaryGenerator.repository.HighlightRepository;
import com.example.VideoToSummaryGenerator.repository.JobRepository;
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

    private final VideoAssetRepository videoRepo;
    private final JobRepository jobRepository;
    private final ArtifactRepository artifactRepo;
    private final HighlightRepository highlightRepo;

    private final VideoMapper videoMapper;
    private final ArtifactMapper artifactMapper;
    private final HighlightMapper highlightMapper;

    public String uploadVideo(MultipartFile file) throws IOException {
        Long timeStamp=System.currentTimeMillis();

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
    public VideoResponseDto getVideoById(Long id) {

        VideoAsset video = videoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));

        return videoMapper.toDto(video);
    }

    // 🔹 Get artifacts of a video
    public List<ArtifactResponseDto> getArtifacts(Long videoId) {

        // Optional: validate video exists
        if (!videoRepo.existsById(videoId)) {
            throw new ResourceNotFoundException("Video not found with id: " + videoId);
        }

        return artifactRepo.findByVideoId(videoId)
                .stream()
                .map(ArtifactMapper::toDTO)
                .toList();
    }


    // 🔹 Get highlights of a video
    public List<HighlightResponseDto> getHighlights(Long videoId) {

        if (!videoRepo.existsById(videoId)) {
            throw new ResourceNotFoundException("Video not found with id: " + videoId);
        }

        return highlightRepo.findByVideoId(videoId)
                .stream()
                .map(HighlightMapper::toDTO)
                .toList();
    }
}
