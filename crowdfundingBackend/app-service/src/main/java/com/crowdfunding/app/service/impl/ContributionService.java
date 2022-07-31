package com.crowdfunding.app.service.impl;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crowdfunding.app.dao.ContributionDao;
import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.service.IContributionService;
import com.crowdfunding.app.service.IProjectService;
import com.crowdfunding.app.service.IUserService;
import com.crowdfunding.app.util.ContributionMapper;
import com.crowdfunding.app.util.ProjectMapper;
import com.crowdfunding.app.util.UserMapper;
import com.crowdfunding.app.validations.IContributionIOValidator;
import com.crowdfunding.common.dto.ContributionRequestDTO;
import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserResponseDTO;
import com.crowdfunding.common.enums.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContributionService implements IContributionService{

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private ContributionMapper contribMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private IContributionIOValidator validator;
	
	@Autowired
	private ContributionDao contributionDao;
	
	@Transactional
	@Override
	public ContributionResponseDTO createContribution(String tokenHeader, ContributionRequestDTO contributionIO) {
		log.info("ContributionService::CREATE_CONTRIBUTION Recieved");
		
		validator.validate(contributionIO);
		Contribution contrb = contribMapper.fromRequestDTO(contributionIO);
		
		User usr = userService.findCurrentlyLoggedInUser(tokenHeader);
		Project proj = projectService.getProjectById(contributionIO.getProjectId().toString());
		
		if(proj == null) {log.info("ProjectController::CREATE_CONTRIBUTION User or Project Not found ,Couldnt Create Contribution"); throw new RequestNotProperException("Couldnot create contribution");};
		if(ProjectStatus.ARCHIVED.equals(proj.getStatus()) || ProjectStatus.CLOSED.equals(proj.getStatus())) {
			log.info("ProjectController::CREATE_CONTRIBUTION Not a Open Project to Fund "+proj.getProjectId());
			throw new RequestNotProperException("Not a Open Project to fund");
		};
		
		contrb.setContributor(usr);
		contrb.setProject(proj);
		
		//Adding contribution to project 
		
		if(proj.getAmountCollected()+contrb.getContributionAmount() >= proj.getAmountRequested()) {
			proj.setStatus(ProjectStatus.ARCHIVED);
		}
		
		proj.setAmountCollected(proj.getAmountCollected()+contrb.getContributionAmount());
		
		//Save Contribution
		Contribution savedContr = contributionDao.save(contrb);	
		
		if(savedContr == null) throw new RequestNotProperException("Couldnot Save Contribution");
		
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
		
		ProjectResponseDTO projResp = projectMapper.toResponseDTO(proj);
		UserResponseDTO usrResp = userMapper.toResponseDTO(usr);
		
		ContributionResponseDTO respObj = contribMapper.toResponseDTO(savedContr);
		
		respObj.setProject(projResp);
		respObj.setContributor(usrResp);
		
		
		return respObj;
	}
	
}
