package com.demo.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = {"com.demo"})
public class ApiConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiConnectApplication.class, args);
	}

//	@Bean 
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//	    return new BCryptPasswordEncoder(); 
//	}
}
