package com.example.VideoToSummaryGenerator.repository;

import com.example.VideoToSummaryGenerator.entity.Artifact;
import com.example.VideoToSummaryGenerator.entity.Highlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long> {
    List<Artifact> findByVideoId(Long videoId);
}
