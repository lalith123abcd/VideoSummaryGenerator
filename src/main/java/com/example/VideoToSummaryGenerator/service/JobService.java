package com.example.VideoToSummaryGenerator.service;


import com.example.VideoToSummaryGenerator.entity.*;
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
public class JobService {


    private final JobRepository jobRepository;
    private final VideoAssetRepository videoAssetRepository;

    private final TranscriptService transcriptService;
    private final SummaryService summaryService;
    private final HighlightService highlightService;
    private final ClipService clipService;
    private final ScreenShotService screenShotService;

    @Scheduled(fixedDelay = 5000)
    public void processQueuedJobs(){
        List<Job> queuedJobs=jobRepository.findByStatus(JobStatus.QUEUED);

        for(Job job:queuedJobs){
            try{
                System.out.println("Processing Job: " + job.getId());

                job.setStatus(JobStatus.PROCESSING);
                jobRepository.save(job);

                Thread.sleep(3000);

               VideoAsset video=job.getVideo();

                transcriptService.generate(video);
                summaryService.generate(video);
                highlightService.generate(video);
                clipService.generate(video);
                screenShotService.generate(video);









                job.setStatus(JobStatus.SUCCESS);
                jobRepository.save(job);

                video.setStatus(VideoStatus.DONE);
                videoAssetRepository.save(video);


                System.out.println("Job Completed: " + job.getId());

            } catch (Exception e) {
                job.setRetryCount(job.getRetryCount()+1);
                if(job.getRetryCount()>=3){
                    job.setStatus(JobStatus.FAILED);
                    VideoAsset video=job.getVideo();
                    video.setStatus(VideoStatus.FAILED);
                    videoAssetRepository.save(video);
                }
                else {
                    job.setStatus(JobStatus.QUEUED);
                }
                jobRepository.save(job);
            }
        }
    }

}
