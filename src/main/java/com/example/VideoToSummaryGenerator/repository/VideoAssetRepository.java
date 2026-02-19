package com.example.VideoToSummaryGenerator.repository;

import com.example.VideoToSummaryGenerator.entity.VideoAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoAssetRepository extends JpaRepository<VideoAsset, Long> {

    boolean existsById(Long id);
}
