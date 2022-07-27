package com.crowdfunding.app.dto;

import java.util.Set;

import com.crowdfunding.app.entity.Contribution;
import com.crowdfunding.app.entity.User;

public class UserContributionsDto {

	private User user;
	private Set<Contribution> userContributions;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Contribution> getUserContributions() {
		return userContributions;
	}

	public void setUserContributions(Set<Contribution> userContributions) {
		this.userContributions = userContributions;
	}

	public UserContributionsDto() {
		
	}
	
	public UserContributionsDto(User user,Set<Contribution> userContributions) {
		super();
		this.user=user;
		this.userContributions=userContributions;
	}
}
