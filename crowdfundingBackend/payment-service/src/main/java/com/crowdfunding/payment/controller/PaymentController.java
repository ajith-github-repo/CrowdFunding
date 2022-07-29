package com.crowdfunding.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.crowdfunding.common.dto.ContributionRequestDTO;
import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.enums.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

	
	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping()
	public ResponseEntity<ContributionResponseDTO> contribute(@RequestHeader(name = "authorization") String tokenHeader,@RequestBody ContributionRequestDTO contributeIO) {
		
		log.info("PaymentController::CONTRIBUTE Recieved");
		contributeIO.validateInput();
		
		ProjectResponseDTO proj =  fetchProject(tokenHeader, contributeIO.getProjectId());
		
		
		if(proj.getStatus().equals(ProjectStatus.CLOSED) || proj.getAmountCollected() >= proj.getAmountRequested()) {
			log.info("PaymentController::CONTRIBUTE Cant fund a closed Project "+proj.getProjectId());
			throw new RequestNotProperException("Cant Fund a Closed Project");
		}
		
		
		//Redirect to payment gateway and do payment
		
		ContributionResponseDTO contrObj = saveContribution(tokenHeader, contributeIO);
		
		log.info("PaymentController::CONTRIBUTE Success");
		return new ResponseEntity<ContributionResponseDTO>(contrObj,HttpStatus.CREATED);
	}
	
	private ProjectResponseDTO fetchProject(String tokenHeader, Long projectId) {
		log.info("PaymentController::FETCH_PROJECT for "+projectId);
		HttpHeaders headers = new HttpHeaders();
		headers.set("authorization", tokenHeader);      

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<ProjectResponseDTO> resp = restTemplate.exchange("http://APP-SERVICE/api/projects/"+projectId,HttpMethod.GET,request, ProjectResponseDTO.class, headers);
		
		if(resp.getStatusCode().isError()) {log.info("PaymentController::FETCH_PROJECT  Couldnot Fetch project for validation for "+projectId);throw new RequestNotProperException("Couldnt Fetch Project for validation");};
		
		ProjectResponseDTO proj = resp.getBody();
		
		if(proj == null) {log.info("PaymentController::FETCH_PROJECT Couldnot Fetch project for validation "+projectId); throw new RequestNotProperException("Couldnt Fetch Project for validation");};
	
		return proj;
	}
	
	private ContributionResponseDTO saveContribution(String tokenHeader, ContributionRequestDTO contributeIO) {
		log.info("PaymentController::SAVE_CONTRIBUTION");
		HttpHeaders headers = new HttpHeaders();
		headers.set("authorization", tokenHeader);      

		HttpEntity<ContributionRequestDTO> contribRequest = new HttpEntity<>(contributeIO, headers);

		ResponseEntity<ContributionResponseDTO> contribResp = restTemplate.postForEntity("http://APP-SERVICE/api/contributions/", contribRequest, ContributionResponseDTO.class);
		
		ContributionResponseDTO contrObj = contribResp.getBody();
		
		if(contrObj == null) {
			log.info("PaymentController::SAVE_CONTRIBUTION Couldnt Save Contribution");
			throw new RequestNotProperException("Couldnt Save Contribution");
		}
		return contrObj;
	}
}
