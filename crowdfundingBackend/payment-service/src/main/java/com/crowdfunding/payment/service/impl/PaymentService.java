package com.crowdfunding.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.crowdfunding.common.dto.ContributionRequestDTO;
import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.PaymentRequestDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.enums.ProjectStatus;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.payment.dao.PaymentDao;
import com.crowdfunding.payment.entity.Payment;
import com.crowdfunding.payment.enums.PaymentMethod;
import com.crowdfunding.payment.service.IPaymentService;
import com.crowdfunding.payment.util.PaymentMapper;
import com.crowdfunding.payment.validations.IPaymentsIOValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService implements IPaymentService{

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	IPaymentsIOValidator validator;
	
	@Autowired
	PaymentMapper paymentMapper;
	
	
	@Autowired
	PaymentDao paymentDao;
	
	@Override
	public ContributionResponseDTO pay(String tokenHeader,PaymentRequestDTO paymentIO) {
		
		log.info("PaymentController::PAY Recieved");
		validator.validate(paymentIO);
		Payment payment = paymentMapper.fromRequestDTO(paymentIO);
		
		ProjectResponseDTO proj =  this.fetchProject(tokenHeader,payment.getPayeeId());
		
		
		if(proj.getStatus().equals(ProjectStatus.CLOSED) || proj.getAmountCollected() >= proj.getAmountRequested()) {
			log.info("PaymentController::CONTRIBUTE Cant fund a closed Project "+proj.getProjectId());
			throw new RequestNotProperException("Cant Fund a Closed Project");
		}
	
		payment.setPaymentMethod(PaymentMethod.CARD);
		Payment savedPayment = paymentDao.save(payment);
		
		if(savedPayment == null) throw new RequestNotProperException("Couldnt save Payment");
		
		ContributionResponseDTO contrObj = this.saveContribution(tokenHeader, paymentIO);
		
		return contrObj;
	}

	
	private ProjectResponseDTO fetchProject(String tokenHeader, Long projectId) {
		log.info("PaymentService::FETCH_PROJECT for "+projectId);
		HttpHeaders headers = new HttpHeaders();
		headers.set("authorization", tokenHeader);      

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<ProjectResponseDTO> resp = restTemplate.exchange("http://APP-SERVICE/api/projects/"+projectId,HttpMethod.GET,request, ProjectResponseDTO.class, headers);
		
		if(resp.getStatusCode().isError()) {log.info("PaymentService::FETCH_PROJECT  Couldnot Fetch project for validation for "+projectId);throw new RequestNotProperException("Couldnt Fetch Project for validation");};
		
		ProjectResponseDTO proj = resp.getBody();
		
		if(proj == null) {log.info("PaymentService::FETCH_PROJECT Couldnot Fetch project for validation "+projectId); throw new RequestNotProperException("Couldnt Fetch Project for validation");};
	
		return proj;
	}
	
	private ContributionResponseDTO saveContribution(String tokenHeader, PaymentRequestDTO paymentIO) {
		log.info("PaymentService::SAVE_CONTRIBUTION");
		HttpHeaders headers = new HttpHeaders();
		headers.set("authorization", tokenHeader);      

		ContributionRequestDTO contributeIO = new ContributionRequestDTO();
		contributeIO.setContributionAmount(paymentIO.getAmount());
		contributeIO.setContributorId(paymentIO.getPayerId());
		contributeIO.setProjectId(paymentIO.getPayeeId());
		
		
		HttpEntity<ContributionRequestDTO> contribRequest = new HttpEntity<>(contributeIO, headers);

		ResponseEntity<ContributionResponseDTO> contribResp = restTemplate.postForEntity("http://APP-SERVICE/api/contributions/", contribRequest, ContributionResponseDTO.class);
		
		ContributionResponseDTO contrObj = contribResp.getBody();
		
		if(contrObj == null) {
			log.info("PaymentService::SAVE_CONTRIBUTION Couldnt Save Contribution");
			throw new RequestNotProperException("Couldnt Save Contribution");
		}
		return contrObj;
	}
}
