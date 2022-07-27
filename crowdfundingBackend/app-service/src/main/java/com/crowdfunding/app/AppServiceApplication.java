package com.crowdfunding.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.crowdfunding.common.security.JwtConfig;

@SpringBootApplication
public class AppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServiceApplication.class, args);
	}
	
	@Bean
  	public JwtConfig jwtConfig() {
    	   return new JwtConfig();
  	}	

}
