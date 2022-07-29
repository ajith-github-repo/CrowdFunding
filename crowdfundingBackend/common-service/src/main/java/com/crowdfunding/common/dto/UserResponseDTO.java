package com.crowdfunding.common.dto;

import java.util.Set;

public class UserResponseDTO {

	private Long userId;
	private String userEmail;
	private String lastName;
	private String firstName;
	private Set<ProjectResponseDTO> projectsFunded;
	private Set<ProjectResponseDTO> projectsOwned;
    private Set<ContributionResponseDTO> contributions;
    
    
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public UserResponseDTO(Long userId, String userEmail, String lastName, String firstName) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.lastName = lastName;
		this.firstName = firstName;
	}
	
	public UserResponseDTO() {
		super();
		
	}
	public Set<ProjectResponseDTO> getProjectsFunded() {
		return projectsFunded;
	}
	public void setProjectsFunded(Set<ProjectResponseDTO> projectsFunded) {
		this.projectsFunded = projectsFunded;
	}
	public Set<ProjectResponseDTO> getProjectsOwned() {
		return projectsOwned;
	}
	public void setProjectsOwned(Set<ProjectResponseDTO> projectsOwned) {
		this.projectsOwned = projectsOwned;
	}
	public Set<ContributionResponseDTO> getContributions() {
		return contributions;
	}
	public void setContributions(Set<ContributionResponseDTO> contributions) {
		this.contributions = contributions;
	}
	
}
