package com.crowdfunding.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crowdfunding.app.service.impl.ContributionService;
import com.crowdfunding.common.dto.ContributionRequestDTO;
import com.crowdfunding.common.dto.ContributionResponseDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/contributions")
@Slf4j
public class ContributionController {

	@Autowired
	private ContributionService contributionService;	
	
	@PostMapping
	public ResponseEntity<ContributionResponseDTO> createContribution(@RequestHeader(name = "authorization") String tokenHeader,@RequestBody ContributionRequestDTO contributionIO){
	
		log.info("ProjectController::CREATE_CONTRIBUTION Recieved");
		
		ContributionResponseDTO respObj = contributionService.createContribution(tokenHeader,contributionIO);
		
		log.info("ProjectController::CREATE_CONTRIBUTION SUCCESS ");
		return new ResponseEntity<ContributionResponseDTO>(respObj,HttpStatus.CREATED);
	}
}
