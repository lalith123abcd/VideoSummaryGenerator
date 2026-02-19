package com.example.VideoToSummaryGenerator.repository;

import com.example.VideoToSummaryGenerator.entity.Job;
import com.example.VideoToSummaryGenerator.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByStatus(JobStatus status);
}
