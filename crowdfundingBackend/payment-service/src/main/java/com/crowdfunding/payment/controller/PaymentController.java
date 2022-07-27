package com.crowdfunding.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
import com.crowdfunding.common.exceptions.RequestNotProperException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	
	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping()
	public ResponseEntity<ContributionResponseObject> contribute(@RequestHeader(name = "authorization") String tokenHeader,@RequestBody ContributionRequestObject contributeIO) {
		
		contributeIO.validateInput();
		
		//Redirect to payment gateway and do payment
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("authorization", tokenHeader);      

		HttpEntity<ContributionRequestObject> request = new HttpEntity<>(contributeIO, headers);

		ResponseEntity<ContributionResponseObject> resp = restTemplate.postForEntity("http://APP-SERVICE/api/contributions/", request, ContributionResponseObject.class);
		
		ContributionResponseObject contrObj = resp.getBody();
		
		if(contrObj == null) throw new RequestNotProperException("Couldnt Save Contribution");
		
		return new ResponseEntity<ContributionResponseObject>(contrObj,HttpStatus.CREATED);
	}
	
}
