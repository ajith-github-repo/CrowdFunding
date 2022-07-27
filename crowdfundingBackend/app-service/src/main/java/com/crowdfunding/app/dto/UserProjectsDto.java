package com.crowdfunding.app.dto;

import java.util.Set;

import com.crowdfunding.app.entity.Project;
import com.crowdfunding.app.entity.User;

public class UserProjectsDto {

	private User user;
	private Set<Project> userProjects;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Project> getUserProjects() {
		return userProjects;
	}
	public void setUserProjects(Set<Project> userProjects) {
		this.userProjects = userProjects;
	}
	public UserProjectsDto(User user, Set<Project> userProjects) {
		super();
		this.user = user;
		this.userProjects = userProjects;
	}
	
	public UserProjectsDto() {
		
	}
	
}
