package com.crowdfunding.auth.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.crowdfunding.auth.dao.AuthDao;
import com.crowdfunding.auth.entity.UserAuth;
import com.crowdfunding.auth.exceptions.UserAlreadyExistsException;
import com.crowdfunding.auth.service.IAuthService;
import com.crowdfunding.auth.util.Encoder;
import com.crowdfunding.auth.util.UserAuthMapper;
import com.crowdfunding.auth.validations.IAuthIOValidator;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.dto.UserResponseDTO;
import com.crowdfunding.common.exceptions.RequestNotProperException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService implements IAuthService{

	@Autowired
	private AuthDao authDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Encoder encoder;
	
	@Autowired
	private UserAuthMapper userMapper;
	
	@Autowired
	private IAuthIOValidator validator;
	
	public UserResponseDTO saveAuth(UserRequestDTO userIO) {
		validator.validate(userIO);
		
		Optional<UserAuth> dbUser = findAuthUsingEmail(userIO.getUserEmail());
		
		if(dbUser.isPresent()) {
			log.info("AuthController::SAVE_USER User Already Exists "+userIO.getUserEmail());
			throw new UserAlreadyExistsException(userIO.getUserEmail()+" Already Exists");
		}
		
		String hashedPassword = encoder.encode((CharSequence)userIO.getPassword());

		userIO.setPassword("");
		
		ResponseEntity<UserResponseDTO> resp = restTemplate.postForEntity("http://APP-SERVICE/api/users/", userIO, UserResponseDTO.class);
	    UserResponseDTO savedUser = resp.getBody();
		
		if(savedUser == null) {
			log.info("AuthController::SAVE_USER Couldnt create user, App Service Returned NULL");
			throw new RequestNotProperException("Couldnt create User");
		
		}
		
	
		UserAuth userAuth = userMapper.fromRequestDTO(userIO);
		userAuth.setPassword(hashedPassword);
		userAuth.setUserEmail(userIO.getUserEmail());
		authDao.save(userAuth);
		
		return savedUser;
	}

	@Override
	public Optional<UserAuth> findAuthUsingEmail(String userEmail) {
		return authDao.findAuthUsingEmail(userEmail);
	}
	
}
 