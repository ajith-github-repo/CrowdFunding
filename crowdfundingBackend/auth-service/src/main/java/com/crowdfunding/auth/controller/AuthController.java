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

import com.crowdfunding.auth.entity.UserAuth;
import com.crowdfunding.auth.exceptions.UserAlreadyExistsException;
import com.crowdfunding.auth.service.IAuthService;
import com.crowdfunding.auth.util.Encoder;
import com.crowdfunding.auth.util.UserAuthMapper;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.dto.UserResponseDTO;
import com.crowdfunding.common.exceptions.RequestNotProperException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

	@Autowired
	private IAuthService authService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Encoder encoder;
	
	@Autowired
	private UserAuthMapper userMapper;
	
	@PostMapping("/signUp")
	public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userIO) {

		log.info("AuthController::SAVE_USER Recieved");
		userIO.validateInput();
		
		Optional<UserAuth> dbUser = authService.findAuthUsingEmail(userIO.getUserEmail());
		
		if(dbUser.isPresent()) {
			log.info("AuthController::SAVE_USER User Already Exists "+userIO.getUserEmail());
			throw new UserAlreadyExistsException(userIO.getUserEmail()+" Already Exists");
		}
		
		
		String hashedPassword = encoder.encode((CharSequence)userIO.getPassword());

		userIO.setPassword("");
		
		ResponseEntity<UserResponseDTO> resp = restTemplate.postForEntity("http://APP-SERVICE/api/users/", userIO, UserResponseDTO.class);
	    UserResponseDTO savedUser = resp.getBody();
		
		if(savedUser == null) {
			log.info("AuthController::SAVE_USER Couldnt create user, App Service Returned NULL");
			throw new RequestNotProperException("Couldnt create User");
		
		}
		
	
		UserAuth userAuth = userMapper.fromRequestDTO(userIO);
		userAuth.setPassword(hashedPassword);
		userAuth.setUserEmail(userIO.getUserEmail());
		authService.saveAuth(userAuth);
		
		log.info("AuthController::SAVE_USER SUCCESS");
		return new ResponseEntity<UserResponseDTO>(savedUser,HttpStatus.CREATED);
	}
	
}
