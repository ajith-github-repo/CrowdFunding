package com.crowdfunding.app.validations;

import com.crowdfunding.common.dto.UserRequestDTO;

public interface IUserIOValidator {

	public void validate(UserRequestDTO userIO);
	public Long validate(String userId);

}
