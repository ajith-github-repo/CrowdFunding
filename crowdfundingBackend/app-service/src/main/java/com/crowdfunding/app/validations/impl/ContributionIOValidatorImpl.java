package com.crowdfunding.app.validations.impl;

import org.springframework.stereotype.Component;

import com.crowdfunding.app.validations.IContributionIOValidator;
import com.crowdfunding.common.dto.ContributionRequestDTO;
import com.crowdfunding.common.exceptions.DataValidationException;

@Component
public class ContributionIOValidatorImpl implements IContributionIOValidator{

	public void validate(ContributionRequestDTO contrib) {
		if(contrib.getContributionAmount() == null || contrib.getContributionAmount() <= 0L)
			throw new DataValidationException("Contribution cannot be less than zero");
		
		if(contrib.getContributorId() == null)
			throw new DataValidationException("Contributor Information is Missing");
		
		if(contrib.getProjectId() == null)
			throw new DataValidationException("Project Info missing from contribution");

	}
}
