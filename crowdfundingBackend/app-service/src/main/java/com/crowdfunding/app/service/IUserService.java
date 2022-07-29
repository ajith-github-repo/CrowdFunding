package com.crowdfunding.app.service;

import java.util.List;
import java.util.Optional;

import com.crowdfunding.app.entity.User;

public interface IUserService {
	public User saveUser(User user);

	public User getUserDetails(Long id);

	public Optional<User> findUserUsingEmail(String userEmail);

	public List<Long> findUserFundedProjectIds(Long userId);
	
	
	public List<Long> findUserOwnedProjectIds(Long userId);
}
