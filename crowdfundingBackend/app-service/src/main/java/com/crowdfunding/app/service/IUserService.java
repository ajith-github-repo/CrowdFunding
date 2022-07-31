package com.crowdfunding.app.service;

import java.util.Set;

import com.crowdfunding.app.entity.User;
import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.dto.UserResponseDTO;

public interface IUserService {
	public UserResponseDTO saveUser(UserRequestDTO user);

	public UserResponseDTO getUserDetails(String userId);

	public Set<ContributionResponseDTO> getUserContributions(String tokenHeader,String userId);
	
	public Set<ProjectResponseDTO> getAllUserProjects(String tokenHeader,String userId);
	
	public UserResponseDTO getCurrentlyLoggedInUserDetails(String tokenHeader); 
	
	public User findCurrentlyLoggedInUser(String tokenHeader);

	User save(User user);
}
