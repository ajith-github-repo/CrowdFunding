package com.crowdfunding.auth.validations;

import com.crowdfunding.common.dto.UserRequestDTO;

public interface IAuthIOValidator {

	public void validate(UserRequestDTO userIO);

}
