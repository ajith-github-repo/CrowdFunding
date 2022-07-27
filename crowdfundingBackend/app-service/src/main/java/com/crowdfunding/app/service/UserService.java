package com.crowdfunding.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crowdfunding.app.dao.UserDao;
import com.crowdfunding.app.entity.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public User saveUser(User user) {
		return userDao.save(user);
	}

	public User getUserDetails(Long id) {

		Optional<User> user = userDao.findById(id);

		if (user.isPresent()) {
			return user.get();
		}

		return null;
	}

	public Optional<User> findUserUsingEmail(String userEmail) {

		return userDao.findUserUsingEmail(userEmail);
	}

	public List<Long> findUserFundedProjectIds(Long userId) {

		Optional<List<Long>> resp = userDao.findUserFundedProjectIds(userId);
		return resp.isPresent() ? resp.get() : null;
	}
	
	
	public List<Long> findUserOwnedProjectIds(Long userId) {

		Optional<List<Long>> resp = userDao.findUserOwnedProjectIds(userId);
		return resp.isPresent() ? resp.get() : null;
	}
	
	
}
