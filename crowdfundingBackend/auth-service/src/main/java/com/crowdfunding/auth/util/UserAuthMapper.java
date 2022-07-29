package com.crowdfunding.auth.util;

import org.springframework.stereotype.Component;

import com.crowdfunding.auth.entity.UserAuth;
import com.crowdfunding.common.dto.UserRequestDTO;


@Component
public class UserAuthMapper {
	
	public UserAuth fromRequestDTO(UserRequestDTO reqDTO) {
		
		UserAuth userAuth = new UserAuth();
		userAuth.setUserEmail(reqDTO.getUserEmail());
		return userAuth;
	}
	
}
