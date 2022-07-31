package com.crowdfunding.app.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crowdfunding.app.service.IUserService;
import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.dto.UserResponseDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

	@Autowired
	IUserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userIO) {
		log.info("UserController::SAVE_USER -> recieved with name : "+userIO.getFirstName());
		UserResponseDTO user = userService.saveUser(userIO);
		
		log.info("UserController::SAVE_USER Could not save User: "+userIO.getFirstName());
		
		return new ResponseEntity<UserResponseDTO>(user, HttpStatus.OK);	
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDTO> getUserDetails(@PathVariable String userId) {
		log.info("UserController::GET_USER_DETAILS with ID: "+userId);
	
		UserResponseDTO user = userService.getUserDetails(userId);
		
		log.info("UserController::GET_USER_DETAILS : "+userId +" SUCCESS");
		return new ResponseEntity<UserResponseDTO>(user, HttpStatus.OK);
	}
	
	
	@GetMapping("/{userId}/contributions")
	public ResponseEntity<Set<ContributionResponseDTO>> getUserContributions(@RequestHeader(name = "authorization") String tokenHeader,@PathVariable String userId) {
		log.info("UserController::GET_USER_CONTRIBUTIONS with ID: "+userId);
		
		Set<ContributionResponseDTO> userContributions = userService.getUserContributions(tokenHeader, userId);
		
		return new ResponseEntity<Set<ContributionResponseDTO>>(userContributions, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/projects")
	public ResponseEntity<Set<ProjectResponseDTO>> getAllUserProjects(@RequestHeader(name = "authorization") String tokenHeader,@PathVariable String userId) {
		log.info("UserController::GET_ALL_USER_PROJECTS with ID: "+userId);
		
		Set<ProjectResponseDTO> userProjects = userService.getAllUserProjects(tokenHeader, userId);
		
		log.info("UserController::GET_ALL_USER_PROJECTS SUCCESS "+userId);
		return new ResponseEntity<Set<ProjectResponseDTO>>(userProjects, HttpStatus.OK);
	}
	
	
	@GetMapping
	public ResponseEntity<UserResponseDTO> getCurrentlyLoggedInUser(@RequestHeader(name = "authorization") String tokenHeader) {
		log.info("UserController::GET_CURRENTLY_LOGGED_USER");
		
		UserResponseDTO usr = userService.getCurrentlyLoggedInUserDetails(tokenHeader);
		
        log.info("UserController::GET_CURRENTLY_LOGGED_USER Successfully retrived User with id "+usr.getUserId());
        
		return new ResponseEntity<UserResponseDTO>(usr, HttpStatus.OK);
	}
  
}
