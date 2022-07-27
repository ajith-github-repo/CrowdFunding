package com.crowdfunding.auth.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.crowdfunding.auth.dto.UserResponseObject;
import com.crowdfunding.auth.entity.UserAuth;
import com.crowdfunding.auth.exceptions.UserAlreadyExistsException;
import com.crowdfunding.auth.service.AuthService;
import com.crowdfunding.auth.util.Encoder;
import com.crowdfunding.common.dto.UserRequestObject;
import com.crowdfunding.common.exceptions.RequestNotProperException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Encoder encoder;
	
	@PostMapping("/signUp")
	public ResponseEntity<UserResponseObject> saveUser(@RequestBody UserRequestObject userIO) {
		
		userIO.validateInput();
		
		Optional<UserAuth> dbUser = authService.findAuthUsingEmail(userIO.getUserEmail());
		
		if(dbUser.isPresent()) {
			throw new UserAlreadyExistsException(userIO.getUserEmail()+" Already Exists");
		}
		
		
		String hashedPassword = encoder.encode((CharSequence)userIO.getPassword());

		userIO.setPassword("");
		
		ResponseEntity<UserResponseObject> resp = restTemplate.postForEntity("http://APP-SERVICE/api/users/", userIO, UserResponseObject.class);
	    UserResponseObject savedUser = resp.getBody();
		
		if(savedUser == null) throw new RequestNotProperException("Couldnt create User");
		
		UserAuth userAuth = new UserAuth();
		userAuth.setPassword(hashedPassword);
		userAuth.setUserEmail(userIO.getUserEmail());
		authService.saveAuth(userAuth);
		
		return new ResponseEntity<UserResponseObject>(savedUser,HttpStatus.CREATED);
	}
	
}
