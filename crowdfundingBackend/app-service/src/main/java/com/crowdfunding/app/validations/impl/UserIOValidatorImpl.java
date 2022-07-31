package com.crowdfunding.app.validations.impl;

import org.springframework.stereotype.Component;

import com.crowdfunding.app.validations.IUserIOValidator;
import com.crowdfunding.common.constants.Constants;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.exceptions.DataValidationException;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.common.util.RegexHelper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserIOValidatorImpl implements IUserIOValidator{

	public void validate(UserRequestDTO userIO) {
		if(userIO.getFirstName() == null || userIO.getFirstName().trim().length() == 0) throw new DataValidationException("Invalid FirstName");
		if(userIO.getLastName() == null || userIO.getLastName().trim().length() == 0) throw new DataValidationException("Invalid Lastname");
		
        Boolean isValidEmail = RegexHelper.validate(userIO.getUserEmail(), Constants.VALID_EMAIL_ADDRESS_REGEX);
		
		if(!isValidEmail) throw new DataValidationException("Invalid UserEmail Format");
		
		Boolean isValidPassword = RegexHelper.validate(userIO.getPassword(), Constants.VALID_PASSWORD_REGEX);

		if(!isValidPassword) throw new DataValidationException("Invalid Password Format");

	}

	@Override
	public void validate(String userId) {
		try {
		    Long.parseLong(userId);
		}catch(NumberFormatException e) {
			log.info("UserValidator::VALIDATE Invalid ID: "+userId);
			throw new RequestNotProperException("Invalid User Id Recieved "+userId);
		}
	}
}
