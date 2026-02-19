package com.example.VideoToSummaryGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VideoToSummaryGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoToSummaryGeneratorApplication.class, args);
	}

}
