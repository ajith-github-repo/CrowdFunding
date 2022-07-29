package com.crowdfunding.common.dto;

import com.crowdfunding.common.constants.Constants;
import com.crowdfunding.common.exceptions.DataValidationException;
import com.crowdfunding.common.util.RegexHelper;

public class UserRequestDTO implements IRequestObj{

	private Long userId;
	private String firstName;
	private String lastName;
	private String userEmail;
	private String password;
	
	public UserRequestDTO() {
		
	}
	
	public UserRequestDTO(Long userId,String firstName, String lastName, String userEmail,String password) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.password = password;
	}




	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}




	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}




	public String getLastName() {
		return lastName;
	}




	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public void validateInput() {
		if(this.getFirstName() == null || this.getFirstName().trim().length() == 0) throw new DataValidationException("Invalid FirstName");
		if(this.getLastName() == null || this.getLastName().trim().length() == 0) throw new DataValidationException("Invalid Lastname");
		
        Boolean isValidEmail = RegexHelper.validate(this.getUserEmail(), Constants.VALID_EMAIL_ADDRESS_REGEX);
		
		if(!isValidEmail) throw new DataValidationException("Invalid UserEmail Format");
		
		Boolean isValidPassword = RegexHelper.validate(this.getPassword(), Constants.VALID_PASSWORD_REGEX);

		if(!isValidPassword) throw new DataValidationException("Invalid Password Format");
		
		
	}
	
}
