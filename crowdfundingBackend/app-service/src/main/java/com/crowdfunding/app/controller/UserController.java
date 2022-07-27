package com.crowdfunding.app.controller;

import java.util.Optional;
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

import com.crowdfunding.app.dto.UserContributionsDto;
import com.crowdfunding.app.dto.UserProjectsDto;
import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.exceptions.UserNotFoundException;
import com.crowdfunding.app.service.UserService;
import com.crowdfunding.common.dto.UserRequestObject;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.common.security.JwtConfig;
import com.crowdfunding.common.util.JWTHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	JwtConfig jwtConfig;
	
	@PostMapping("/")
	public ResponseEntity<User> saveUser(@RequestBody UserRequestObject userIO) {
		log.info("In Save User method of User Controller");
		
		User user = new User();
		user.setFirstName(userIO.getFirstName());
		user.setLastName(userIO.getLastName());
		user.setUserEmail(userIO.getUserEmail());
		
		User usr = userService.saveUser(user);
		
		if(usr!= null) {
			return new ResponseEntity<User>(usr,HttpStatus.CREATED);
		}
		
		throw new UserNotFoundException();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserDetails(@PathVariable String userId) {
	    
		Long id;
		try {
			id = Long.parseLong(userId);
		}catch(NumberFormatException e) {
			throw new RequestNotProperException("Invalid User Id Recieved");
		}
		
		User user = userService.getUserDetails(id);
		
		if(user == null) throw new UserNotFoundException();
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	@GetMapping("/{userId}/contributions")
	public ResponseEntity<Set<Contribution>> getUserContributions(@RequestHeader(name = "authorization") String tokenHeader,@PathVariable String userId) {
	    
		Long id;
		try {	
			id = Long.parseLong(userId);
		}catch(NumberFormatException e) {
			throw new RequestNotProperException("Invalid User Id Recieved");
		}
		
		User usr = findCurrentlyLoggedInUser(tokenHeader);
		
		if(usr == null) throw new UserNotFoundException();
		if(usr.getUserId() != id) throw new RequestNotProperException("Cannot access other users contributions");
		
		
		Set<Contribution> userContributions = usr.getContributions();
		
		return new ResponseEntity<Set<Contribution>>(userContributions, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/projects")
	public ResponseEntity<Set<Project>> getAllUserProjects(@RequestHeader(name = "authorization") String tokenHeader,@PathVariable String userId) {
	    
		Long id;
		try {
			id = Long.parseLong(userId);
		}catch(NumberFormatException e) {
			throw new RequestNotProperException("Invalid User Id Recieved");
		}
		
		User usr = findCurrentlyLoggedInUser(tokenHeader);
		
		if(usr == null) throw new UserNotFoundException();
		if(usr.getUserId() != id) throw new RequestNotProperException("Cannot access other users projects");
		
		
		Set<Project> userProjects = usr.getProjectsOwned();
		
		return new ResponseEntity<Set<Project>>(userProjects, HttpStatus.OK);
	}
	
	
	@GetMapping("/currentUserInfo")
	public ResponseEntity<User> getCurrentlyLoggedInUser(@RequestHeader(name = "authorization") String tokenHeader) {
	    
        User usr = findCurrentlyLoggedInUser(tokenHeader);
		
		
		return new ResponseEntity<User>(usr, HttpStatus.OK);
	}
	
	
    private User findCurrentlyLoggedInUser(String tokenHeader) {
		
        String userEmail = JWTHelper.getCurrentlyLoggedInUserFromJWT(tokenHeader.replace(jwtConfig.getPrefix(), ""),jwtConfig.getSecret());
		
		Optional<User> userObj = userService.findUserUsingEmail(userEmail);
		
		if(!userObj.isPresent()) throw new UserNotFoundException("User Not found");
		
		User usr = userObj.get();
		
		return usr;
	}
}
