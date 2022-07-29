package com.crowdfunding.app.controller;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.exceptions.UserNotFoundException;
import com.crowdfunding.app.service.IProjectService;
import com.crowdfunding.app.service.IUserService;
import com.crowdfunding.app.service.impl.ContributionService;
import com.crowdfunding.app.util.ContributionMapper;
import com.crowdfunding.app.util.ProjectMapper;
import com.crowdfunding.app.util.UserMapper;
import com.crowdfunding.common.dto.ContributionRequestDTO;
import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserResponseDTO;
import com.crowdfunding.common.enums.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.common.security.JwtConfig;
import com.crowdfunding.common.util.DateHelper;
import com.crowdfunding.common.util.JWTHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/contributions")
@Slf4j
public class ContributionController {

	@Autowired
	ContributionService contributionService;
	
	@Autowired
	JwtConfig jwtConfig;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IProjectService projectService;
	
	@Autowired
	ProjectMapper projectMapper;
	
	@Autowired
	ContributionMapper contribMapper;
	
	@Autowired
	UserMapper userMapper;
	
	
	@PostMapping
	public ResponseEntity<ContributionResponseDTO> createContribution(@RequestHeader(name = "authorization") String tokenHeader,@RequestBody ContributionRequestDTO contributionIO){
	
		log.info("ProjectController::CREATE_CONTRIBUTION Recieved");
		 
		contributionIO.validateInput();
		
		User usr = findCurrentlyLoggedInUser(tokenHeader);

		Project proj = projectService.getProjectById(contributionIO.getProjectId());
		
		if(proj == null) {log.info("ProjectController::CREATE_CONTRIBUTION User or Project Not found ,Couldnt Create Contribution"); throw new RequestNotProperException("Couldnot create contribution");};

		Contribution contrb = contribMapper.fromRequestDTO(contributionIO);
		contrb.setContributor(usr);
		contrb.setProject(proj);
		
		//Adding contribution to project 
		
		if(ProjectStatus.ARCHIVED.equals(proj.getStatus()) || ProjectStatus.CLOSED.equals(proj.getStatus())) {
			log.info("ProjectController::CREATE_CONTRIBUTION Not a Open Project to Fund "+proj.getProjectId());
			throw new RequestNotProperException("Not a Open Project to fund");
		};
		
		if(proj.getAmountCollected()+contrb.getContributionAmount() >= proj.getAmountRequested()) {
			proj.setStatus(ProjectStatus.ARCHIVED);
		}
		
		proj.setAmountCollected(proj.getAmountCollected()+contrb.getContributionAmount());
		
		//Save Contribution
		Contribution savedContr = contributionService.createContribution(contrb);	
		
		//Add Contribution to Project
		Set<Contribution> contributionsToProject = proj.getContributions();
		contributionsToProject.add(savedContr);
		
		//Add Contribution to User
		Set<Contribution> contributionsByUser = usr.getContributions();
		contributionsByUser.add(savedContr);
	
		//Add Project as one of funded project for User
		Set<Project> projectsFunded = usr.getProjectsFunded();
		projectsFunded.add(proj);
		
		//Add User as one of Funders
		Set<User> funders = proj.getFunders();
	    funders.add(usr);
				
		userService.saveUser(usr);
		projectService.saveProject(proj);
		
		ProjectResponseDTO projResp = projectMapper.toResponseDTO(proj);
		UserResponseDTO usrResp = userMapper.toResponseDTO(usr);
		
		ContributionResponseDTO respObj = contribMapper.toResponseDTO(savedContr);
		
		respObj.setProject(projResp);
		respObj.setContributor(usrResp);
		
		log.info("ProjectController::CREATE_CONTRIBUTION SUCCESS ");
		return new ResponseEntity<ContributionResponseDTO>(respObj,HttpStatus.CREATED);
	}
	
	
     private User findCurrentlyLoggedInUser(String tokenHeader) {
		
        String userEmail = JWTHelper.getCurrentlyLoggedInUserFromJWT(tokenHeader.replace(jwtConfig.getPrefix(), ""),jwtConfig.getSecret());
		
		Optional<User> userObj = userService.findUserUsingEmail(userEmail);
		
		if(!userObj.isPresent()) throw new UserNotFoundException("User Not found, Couldnt create Contribution");
		
		User usr = userObj.get();
		
		return usr;
	}
	
	
}
