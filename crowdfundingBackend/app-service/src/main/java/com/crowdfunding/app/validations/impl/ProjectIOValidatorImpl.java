package com.crowdfunding.app.validations.impl;

import org.springframework.stereotype.Component;

import com.crowdfunding.app.validations.IProjectIOValidator;
import com.crowdfunding.common.dto.ProjectRequestDTO;
import com.crowdfunding.common.exceptions.DataValidationException;

@Component
public class ProjectIOValidatorImpl implements IProjectIOValidator{


	@Override
	public void validate(ProjectRequestDTO projectIO) {
	
		if(projectIO.getAmountRequested() <= 0L)
			throw new DataValidationException("Requested Amount should be greater Zero");
		
		if(projectIO.getExpireDate() == null)
			throw new DataValidationException("Please Provide an Expiry Date");
		
		if(projectIO.getTitle() == null || projectIO.getTitle().length() == 0 || projectIO.getTitle().length() > 50)
			throw new DataValidationException("Title length doesnt meet minimum requirements,  min 1 max 50 characters");
		
		if(projectIO.getDescription() == null || projectIO.getDescription().length() == 0 || projectIO.getDescription().length() > 10000)
			throw new DataValidationException("Description length doesnt meet minimum requirements, min 1 max 10000 characters");
		
		if(projectIO.getTagline() == null || projectIO.getTagline().length() == 0 || projectIO.getTagline().length() > 100)
			throw new DataValidationException("Tagline length doesnt meet minimum requirements, min 1 max 100 characters");
	
	}
}
