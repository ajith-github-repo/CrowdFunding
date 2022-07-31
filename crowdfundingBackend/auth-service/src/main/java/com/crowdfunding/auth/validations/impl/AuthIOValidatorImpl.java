package com.crowdfunding.auth.validations.impl;

import org.springframework.stereotype.Component;

import com.crowdfunding.auth.validations.IAuthIOValidator;
import com.crowdfunding.common.constants.Constants;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.exceptions.DataValidationException;
import com.crowdfunding.common.util.RegexHelper;

@Component
public class AuthIOValidatorImpl implements IAuthIOValidator{

	public void validate(UserRequestDTO userIO) {
		if(userIO.getFirstName() == null || userIO.getFirstName().trim().length() == 0) throw new DataValidationException("Invalid FirstName");
		if(userIO.getLastName() == null || userIO.getLastName().trim().length() == 0) throw new DataValidationException("Invalid Lastname");
		
        Boolean isValidEmail = RegexHelper.validate(userIO.getUserEmail(), Constants.VALID_EMAIL_ADDRESS_REGEX);
		
		if(!isValidEmail) throw new DataValidationException("Invalid UserEmail Format");
		
		Boolean isValidPassword = RegexHelper.validate(userIO.getPassword(), Constants.VALID_PASSWORD_REGEX);

		if(!isValidPassword) throw new DataValidationException("Invalid Password Format");

	}
}
