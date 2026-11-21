package com.snapworld.snapworld_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnapworldServerApplication {

	public static void main(String[] args) {

		// Check whether MongoDB Atlas connection variable exists.
		String mongoUri = System.getenv("MONGODB_URI");

		System.out.println("--------------------------------");
		System.out.println("       SNAPWORLD SERVER");
		System.out.println("--------------------------------");

		if (mongoUri != null && !mongoUri.isBlank()) {
			System.out.println("MONGODB_URI present: true");
		} else {
			System.out.println("MONGODB_URI present: false");
		}

		// Start Spring Boot server.
		SpringApplication.run(
				SnapworldServerApplication.class,
				args
		);
	}
}