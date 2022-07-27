package com.crowdfunding.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crowdfunding.auth.dao.AuthDao;
import com.crowdfunding.auth.entity.UserAuth;

@Service
public class AuthService {

	@Autowired
	private AuthDao authDao;
	
	public UserAuth saveAuth(UserAuth auth) {
	    
		return authDao.save(auth);
	}
		
	
	public Optional<UserAuth> findAuthUsingEmail(String userEmail) {
		
		return authDao.findAuthUsingEmail(userEmail);
     
	}
	
	
	
}
 