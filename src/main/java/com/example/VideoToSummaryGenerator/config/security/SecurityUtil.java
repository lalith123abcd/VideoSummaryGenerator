package com.example.VideoToSummaryGenerator.config.security;

import com.example.VideoToSummaryGenerator.entity.User;
import com.example.VideoToSummaryGenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;

    public Long getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("AUTH: " + auth);
        System.out.println("NAME: " + auth.getName());

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getId();
    }
}