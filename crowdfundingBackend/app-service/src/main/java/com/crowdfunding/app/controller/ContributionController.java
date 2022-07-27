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
import com.crowdfunding.app.service.ContributionService;
import com.crowdfunding.app.service.ProjectService;
import com.crowdfunding.app.service.UserService;
import com.crowdfunding.common.dto.ContributionRequestObject;
import com.crowdfunding.common.dto.ContributionResponseObject;
import com.crowdfunding.common.dto.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.common.security.JwtConfig;
import com.crowdfunding.common.util.DateHelper;
import com.crowdfunding.common.util.JWTHelper;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {

	@Autowired
	ContributionService contributionService;
	
	@Autowired
	JwtConfig jwtConfig;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectService projectService;
	
	@PostMapping
	public ResponseEntity<ContributionResponseObject> createContribution(@RequestHeader(name = "authorization") String tokenHeader,@RequestBody ContributionRequestObject contributionIO){
	
		contributionIO.validateInput();
		
		User usr = findCurrentlyLoggedInUser(tokenHeader);

		Project proj = projectService.getProjectById(contributionIO.getProjectId());
		
		if(usr == null || proj == null) throw new RequestNotProperException("Couldnot create contribution");

		Contribution contrb = new Contribution();
		contrb.setContributionAmount(contributionIO.getContributionAmount());
		contrb.setContributionTime(DateHelper.convertDateToSQLDate(LocalDate.now().toString()));
		contrb.setContributor(usr);
		contrb.setProject(proj);
		contrb.setUserId(usr.getUserId());
		contrb.setProjectId(proj.getProjectId());
		//Adding contribution to project 
		
		if(ProjectStatus.ARCHIVED.equals(proj.getStatus()) || ProjectStatus.CLOSED.equals(proj.getStatus())) throw new RequestNotProperException("Not a Open Project to fund");
		
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
		
		ContributionResponseObject respObj = new ContributionResponseObject(contrb.getContributionId(),contrb.getContributionAmount(),contrb.getContributionTime(),contrb.getProjectId(),contrb.getUserId());
		return new ResponseEntity<ContributionResponseObject>(respObj,HttpStatus.CREATED);
	}
	
	
     private User findCurrentlyLoggedInUser(String tokenHeader) {
		
        String userEmail = JWTHelper.getCurrentlyLoggedInUserFromJWT(tokenHeader.replace(jwtConfig.getPrefix(), ""),jwtConfig.getSecret());
		
		Optional<User> userObj = userService.findUserUsingEmail(userEmail);
		
		if(!userObj.isPresent()) throw new UserNotFoundException("User Not found, Couldnt create Contribution");
		
		User usr = userObj.get();
		
		return usr;
	}
	
	
}
