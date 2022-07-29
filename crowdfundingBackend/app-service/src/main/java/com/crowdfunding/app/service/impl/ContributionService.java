package com.crowdfunding.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crowdfunding.app.dao.ContributionDao;
import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.service.IContributionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContributionService implements IContributionService{

	@Autowired
	ContributionDao contributionDao;

	public Contribution createContribution(Contribution contrb) {
		log.info("ContributionService::CREATE_CONTRIBUTION Recieved");
		return contributionDao.save(contrb);
	}
	
	
}
