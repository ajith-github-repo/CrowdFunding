package com.crowdfunding.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


import com.crowdfunding.auth.util.Encoder;
import com.crowdfunding.common.security.JwtConfig;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public JwtConfig jwtConfig() {
        	return new JwtConfig();
	}
	
	@Bean
	public Encoder encoder() {
	    return new Encoder();
	}
}
