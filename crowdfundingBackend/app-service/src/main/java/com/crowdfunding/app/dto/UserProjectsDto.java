package com.crowdfunding.app.dto;

import java.util.Set;

import com.crowdfunding.common.dto.ProjectResponseDTO;
import com.crowdfunding.common.dto.UserResponseDTO;

public class UserProjectsDTO {

	private UserResponseDTO user;
	private Set<ProjectResponseDTO> userProjects;
	
	
	public UserResponseDTO getUser() {
		return user;
	}
	public void setUser(UserResponseDTO user) {
		this.user = user;
	}
	public Set<ProjectResponseDTO> getUserProjects() {
		return userProjects;
	}
	public void setUserProjects(Set<ProjectResponseDTO> userProjects) {
		this.userProjects = userProjects;
	}
	public UserProjectsDTO(UserResponseDTO user, Set<ProjectResponseDTO> userProjects) {
		super();
		this.user = user;
		this.userProjects = userProjects;
	}
	
	public UserProjectsDTO() {
		
	}
	
}
