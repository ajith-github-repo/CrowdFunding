package com.crowdfunding.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.PaymentRequestDTO;
import com.crowdfunding.payment.service.IPaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

	@Autowired
	private IPaymentService paymentService;
	
	@PostMapping()
	public ResponseEntity<ContributionResponseDTO> contribute(@RequestHeader(name = "authorization") String tokenHeader,@RequestBody PaymentRequestDTO paymentIO) {
		ContributionResponseDTO contrObj = paymentService.pay(tokenHeader,paymentIO);
		log.info("PaymentController::CONTRIBUTE Success");
		return new ResponseEntity<ContributionResponseDTO>(contrObj,HttpStatus.CREATED);
	}
	
}
