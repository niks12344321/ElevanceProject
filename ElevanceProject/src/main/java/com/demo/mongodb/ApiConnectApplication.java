package com.demo.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication(scanBasePackages = {"com.demo"})
public class ApiConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiConnectApplication.class, args);
	}

}
