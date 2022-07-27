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

import com.crowdfunding.common.dto.ContributionRequestObject;
import com.crowdfunding.common.dto.ContributionResponseObject;
import com.crowdfunding.common.dto.ProjectResponseObject;
import com.crowdfunding.common.dto.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	
	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping()
	public ResponseEntity<ContributionResponseObject> contribute(@RequestHeader(name = "authorization") String tokenHeader,@RequestBody ContributionRequestObject contributeIO) {
		
		contributeIO.validateInput();
		
		//
		HttpHeaders headers = new HttpHeaders();
		headers.set("authorization", tokenHeader);      

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<ProjectResponseObject> resp = restTemplate.exchange("http://APP-SERVICE/api/projects/"+contributeIO.getProjectId(),HttpMethod.GET,request, ProjectResponseObject.class, headers);
		
		if(resp.getStatusCode().isError()) throw new RequestNotProperException("Couldnt Fetch Project for validation");
		
		ProjectResponseObject proj = resp.getBody();
		
		if(proj == null) throw new RequestNotProperException("Couldnt Fetch Project for validation");
		
		if(proj.getStatus().equals(ProjectStatus.CLOSED) || proj.getAmountCollected() >= proj.getAmountRequested()) throw new RequestNotProperException("Cant Fund a Closed Project");
		
		
		//Redirect to payment gateway and do payment

		HttpEntity<ContributionRequestObject> contribRequest = new HttpEntity<>(contributeIO, headers);

		ResponseEntity<ContributionResponseObject> contribResp = restTemplate.postForEntity("http://APP-SERVICE/api/contributions/", contribRequest, ContributionResponseObject.class);
		
		ContributionResponseObject contrObj = contribResp.getBody();
		
		if(contrObj == null) throw new RequestNotProperException("Couldnt Save Contribution");
		
		return new ResponseEntity<ContributionResponseObject>(contrObj,HttpStatus.CREATED);
	}
	
}
