package com.crowdfunding.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crowdfunding.auth.service.IAuthService;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.dto.UserResponseDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

	@Autowired
	private IAuthService authService;
	
	@PostMapping("/signUp")
	public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userIO) {

		log.info("AuthController::SAVE_USER Recieved");
		
		UserResponseDTO savedUser = authService.saveAuth(userIO);
		
		log.info("AuthController::SAVE_USER SUCCESS");
		return new ResponseEntity<UserResponseDTO>(savedUser,HttpStatus.CREATED);
	}
	
}
