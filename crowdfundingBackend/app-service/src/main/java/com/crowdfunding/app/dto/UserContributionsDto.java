package com.crowdfunding.app.dto;

import java.util.Set;

import com.crowdfunding.common.dto.ContributionResponseDTO;
import com.crowdfunding.common.dto.UserResponseDTO;

public class UserContributionsDTO {

	private UserResponseDTO user;
	private Set<ContributionResponseDTO> userContributions;
	
	public UserResponseDTO getUser() {
		return user;
	}

	public void setUser(UserResponseDTO user) {
		this.user = user;
	}

	public Set<ContributionResponseDTO> getUserContributions() {
		return userContributions;
	}

	public void setUserContributions(Set<ContributionResponseDTO> userContributions) {
		this.userContributions = userContributions;
	}

	public UserContributionsDTO() {
		
	}
	
	public UserContributionsDTO(UserResponseDTO user,Set<ContributionResponseDTO> userContributions) {
		super();
		this.user=user;
		this.userContributions=userContributions;
	}
}
