package com.crowdfunding.auth.service;

import java.util.Optional;

import com.crowdfunding.auth.entity.UserAuth;

public interface IAuthService {
	
	public UserAuth saveAuth(UserAuth auth);
	public Optional<UserAuth> findAuthUsingEmail(String userEmail);
	
}
