package com.example.VideoToSummaryGenerator.repository;

import com.example.VideoToSummaryGenerator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
