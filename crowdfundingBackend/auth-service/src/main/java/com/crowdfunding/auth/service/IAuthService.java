package com.crowdfunding.auth.service;

import java.util.Optional;

import com.crowdfunding.auth.entity.UserAuth;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.dto.UserResponseDTO;

public interface IAuthService {
	
	public UserResponseDTO saveAuth(UserRequestDTO auth);

	public Optional<UserAuth> findAuthUsingEmail(String userEmail);
	
}
