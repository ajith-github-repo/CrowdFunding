package com.crowdfunding.app.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crowdfunding.app.dao.UserDao;
import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.exceptions.UserNotFoundException;
import com.crowdfunding.app.service.IUserService;
import com.crowdfunding.app.util.ContributionMapper;
import com.crowdfunding.app.util.ProjectMapper;
import com.crowdfunding.app.util.UserMapper;
import com.crowdfunding.app.validations.IUserIOValidator;
import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.dto.UserResponseDTO;
import com.crowdfunding.common.exceptions.RequestNotProperException;
import com.crowdfunding.common.security.JwtConfig;
import com.crowdfunding.common.util.JWTHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements IUserService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ProjectMapper projMapper;
	
	@Autowired
	private ContributionMapper contribMapper;
	
	@Autowired
	private JwtConfig jwtConfig;
	
	@Autowired
	private JWTHelper jwtHelper;
	
	@Autowired
	private IUserIOValidator validator;

	@Override
	public UserResponseDTO saveUser(UserRequestDTO userIO) {
		log.info("UserService::SAVE_USER Recieved");
		validator.validate(userIO);
		User user = userMapper.fromRequestDTO(userIO);
		
		User savedUser = save(user);
		
		if(savedUser!= null) {
			log.info("UserController::SAVE_USER -> User Saved with ID: "+savedUser.getUserId());
			return userMapper.toResponseDTO(savedUser);
		}
		
		throw new RequestNotProperException("Could not save User");
	}

	@Override
	public UserResponseDTO getUserDetails(String userId) {
		log.info("UserService::GET_USER_DETAILS Recieved");
		
		Long id = validator.validate(userId);
		
		Optional<User> user = userDao.findById(id);

		if (!user.isPresent()) {
			log.info("UserController::GET_USER_DETAILS -> User with ID : "+userId +" Not found"); throw new UserNotFoundException();
		}
		
		return userMapper.toResponseDTO(user.get());
	}

	@Override
	public Set<ContributionResponseDTO> getUserContributions(String tokenHeader, String userId) {
		log.info("UserService::GET_USER_CONTRIBUTIONS Recieved");
        
		Long id = validator.validate(userId);
		
		User usr = findCurrentlyLoggedInUser(tokenHeader);
		
		if(!usr.getUserId().equals(id)) { log.info("UserController::GET_USER_CONTRIBUTIONS Not Authorized to Access " +userId +" contributions"); throw new RequestNotProperException("Not Authorized to View the Contribution Details of "+userId);};
		
		Set<Contribution> userContributions= usr.getContributions();
		
		Set<ContributionResponseDTO> contributionsRes = new HashSet<ContributionResponseDTO>(userContributions.size());
	   	 
        for ( Contribution contrib : userContributions ) {
        	
        	ContributionResponseDTO res = contribMapper.toResponseDTO(contrib);
        	res.setProject(projMapper.toResponseDTO(contrib.getProject()));
        	contributionsRes.add(res);
        }
		
		
		return contributionsRes;
	}

	
	@Override
	public Set<ProjectResponseDTO> getAllUserProjects(String tokenHeader, String userId) {
		log.info("UserService::GET_USER_PROJECTS Recieved");
	
		Long id = validator.validate(userId);
		
		User usr = findCurrentlyLoggedInUser(tokenHeader);
		
			
		if(!usr.getUserId().equals(id)) {
			log.info("UserController::GET_ALL_USER_PROJECTS Not Authorized to Access " +userId +" projects");
			throw new RequestNotProperException("Not Authorized to Access " +userId +" projects ");
		}

		
		Set<ProjectResponseDTO> userProjects = usr.getProjectsOwned().stream().map(projMapper::toResponseDTO).collect(Collectors.toSet());
		
		return userProjects;
	}

	@Override
	public UserResponseDTO getCurrentlyLoggedInUserDetails(String tokenHeader) {
		log.info("UserService::GET_CURRENTLY_LOGGED_USER_DETAILS Recieved");
        User usr = findCurrentlyLoggedInUser(tokenHeader);
		
        UserResponseDTO respUser = userMapper.toResponseDTO(usr);
		
		return respUser;
	}
	
	public User findCurrentlyLoggedInUser(String tokenHeader) {
		
        String userEmail = jwtHelper.getCurrentlyLoggedInUserFromJWT(tokenHeader.replace(jwtConfig.getPrefix(), ""),jwtConfig.getSecret());
		
		Optional<User> userObj = userDao.findUserUsingEmail(userEmail);
		
		if(!userObj.isPresent()) throw new UserNotFoundException("User Not found, Couldnt create Contribution");
		
		User usr = userObj.get();
		
		return usr;
   }
	
	@Override
	public User save(User user) {
		return userDao.save(user);
	}
	
}
