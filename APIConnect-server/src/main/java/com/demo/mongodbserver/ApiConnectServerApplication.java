package com.demo.mongodbserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.demo"})
@EnableFeignClients("com.demo.mongodbserver")
public class ApiConnectServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiConnectServerApplication.class, args);
	}

}
