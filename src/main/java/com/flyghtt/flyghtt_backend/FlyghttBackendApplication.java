package com.flyghtt.flyghtt_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FlyghttBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyghttBackendApplication.class, args);
	}
}
