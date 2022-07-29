package com.crowdfunding.app.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.exceptions.UserNotFoundException;
import com.crowdfunding.app.service.IUserService;
import com.crowdfunding.app.util.ContributionMapper;
import com.crowdfunding.app.util.ProjectMapper;
import com.crowdfunding.app.util.UserMapper;
import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.dto.UserResponseDTO;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.common.security.JwtConfig;
import com.crowdfunding.common.util.JWTHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

	@Autowired
	IUserService userService;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	ProjectMapper projMapper;
	
	@Autowired
	ContributionMapper contribMapper;
	
	@Autowired
	JwtConfig jwtConfig;
	
	@PostMapping("/")
	public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userIO) {
		log.info("UserController::SAVE_USER -> recieved with name : "+userIO.getFirstName());
		
		User user = userMapper.fromRequestDTO(userIO);
		
		User savedUser = userService.saveUser(user);
		
		if(savedUser!= null) {
			log.info("UserController::SAVE_USER -> User Saved with ID: "+savedUser.getUserId());
			return new ResponseEntity<UserResponseDTO>(userMapper.toResponseDTO(savedUser),HttpStatus.CREATED);
		}
		
		log.info("UserController::SAVE_USER Could not save User: "+userIO.getFirstName());
		throw new UserNotFoundException();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDTO> getUserDetails(@PathVariable String userId) {
		log.info("UserController::GET_USER_DETAILS with ID: "+userId);
		Long id;
		try {
			id = Long.parseLong(userId);
		}catch(NumberFormatException e) {
			log.info("UserController::GET_USER_DETAILS Invalid ID: "+userId);
			throw new RequestNotProperException("Invalid User Id Recieved "+userId);
		}
		
		User user = userService.getUserDetails(id);
		
		if(user == null) {log.info("UserController::GET_USER_DETAILS -> User with ID : "+userId +" Not found"); throw new UserNotFoundException();};
		
		log.info("UserController::GET_USER_DETAILS : "+userId +" SUCCESS");
		return new ResponseEntity<UserResponseDTO>(userMapper.toResponseDTO(user), HttpStatus.OK);
	}
	
	
	@GetMapping("/{userId}/contributions")
	public ResponseEntity<Set<ContributionResponseDTO>> getUserContributions(@RequestHeader(name = "authorization") String tokenHeader,@PathVariable String userId) {
		log.info("UserController::GET_USER_CONTRIBUTIONS with ID: "+userId);
		Long id;
		try {	
			id = Long.parseLong(userId);
		}catch(NumberFormatException e) {
			log.info("UserController::GET_USER_CONTRIBUTIONS Invalid ID: "+userId);
			throw new RequestNotProperException("Invalid User Id Recieved "+userId);
		}
		
		User usr = findCurrentlyLoggedInUser(tokenHeader);
		
		if(!usr.getUserId().equals(id)) { log.info("UserController::GET_USER_CONTRIBUTIONS Not Authorized to Access " +userId +" contributions"); throw new RequestNotProperException("Not Authorized to View the Contribution Details of "+userId);};
		
		Set<ContributionResponseDTO> userContributions = getUsersAllContributionsResponse(usr.getContributions());
		
		return new ResponseEntity<Set<ContributionResponseDTO>>(userContributions, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/projects")
	public ResponseEntity<Set<ProjectResponseDTO>> getAllUserProjects(@RequestHeader(name = "authorization") String tokenHeader,@PathVariable String userId) {
		log.info("UserController::GET_ALL_USER_PROJECTS with ID: "+userId);
		Long id;
		try {
			id = Long.parseLong(userId);
		}catch(NumberFormatException e) {
			log.info("UserController::GET_ALL_USER_PROJECTS Invalid ID: "+userId);
			throw new RequestNotProperException("Invalid User Id Recieved "+userId);
		}
		
		User usr = findCurrentlyLoggedInUser(tokenHeader);
		
			
		if(!usr.getUserId().equals(id)) {
			log.info("UserController::GET_ALL_USER_PROJECTS Not Authorized to Access " +userId +" projects");
			throw new RequestNotProperException("Not Authorized to Access " +userId +" projects ");
		}

		
		Set<ProjectResponseDTO> userProjects = usr.getProjectsOwned().stream().map(projMapper::toResponseDTO).collect(Collectors.toSet());
		
		log.info("UserController::GET_ALL_USER_PROJECTS SUCCESS "+userId);
		return new ResponseEntity<Set<ProjectResponseDTO>>(userProjects, HttpStatus.OK);
	}
	
	
	@GetMapping("/currentUserInfo")
	public ResponseEntity<UserResponseDTO> getCurrentlyLoggedInUser(@RequestHeader(name = "authorization") String tokenHeader) {
	    
		log.info("UserController::GET_CURRENTLY_LOGGED_USER");
        User usr = findCurrentlyLoggedInUser(tokenHeader);
		
        UserResponseDTO respUser = userMapper.toResponseDTO(usr);
		
        log.info("UserController::GET_CURRENTLY_LOGGED_USER Successfully retrived User with id "+usr.getUserId());
		return new ResponseEntity<UserResponseDTO>(respUser, HttpStatus.OK);
	}
	
	
    private User findCurrentlyLoggedInUser(String tokenHeader) {
		
    	log.info("UserController::FIND_CURRENTLY_LOGGED_IN_USER");
        String userEmail = JWTHelper.getCurrentlyLoggedInUserFromJWT(tokenHeader.replace(jwtConfig.getPrefix(), ""),jwtConfig.getSecret());
		
		Optional<User> userObj = userService.findUserUsingEmail(userEmail);
		
		if(!userObj.isPresent()) {
			log.info("UserController::FIND_CURRENTLY_LOGGED_IN_USER Token User not found");
			throw new UserNotFoundException("User Not found");
		}
		
		User usr = userObj.get();
		
		return usr;
	}
    
    private Set<ContributionResponseDTO> getUsersAllContributionsResponse(Set<Contribution> contirbutions){
   	 Set<ContributionResponseDTO> contributionsRes = new HashSet<ContributionResponseDTO>( contirbutions.size());
   	 
        for ( Contribution contrib : contirbutions ) {
        	
        	ContributionResponseDTO res = contribMapper.toResponseDTO(contrib);
        	res.setProject(projMapper.toResponseDTO(contrib.getProject()));
        	contributionsRes.add(res);
        }
        return contributionsRes;
    }
}
