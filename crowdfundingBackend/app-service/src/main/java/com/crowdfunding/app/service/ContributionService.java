package com.crowdfunding.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crowdfunding.app.dao.ContributionDao;
import com.crowdfunding.app.entity.Contribution;

@Service
public class ContributionService {

	@Autowired
	ContributionDao contributionDao;

	public Contribution createContribution(Contribution contrb) {
		return contributionDao.save(contrb);
	}
	
	
}
