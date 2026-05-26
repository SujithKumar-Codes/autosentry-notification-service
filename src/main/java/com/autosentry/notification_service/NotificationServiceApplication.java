package com.autosentry.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
public class NotificationServiceApplication {

	public static void main(String[] args) {
		System.out.println("🚀 Notification Service Starting...");
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
}