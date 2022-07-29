package com.crowdfunding.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crowdfunding.app.dao.UserDao;
import com.crowdfunding.app.entity.User;
import com.crowdfunding.app.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements IUserService{

	@Autowired
	private UserDao userDao;

	public User saveUser(User user) {
		log.info("UserService::SAVE_USER Recieved");
		return userDao.save(user);
	}

	public User getUserDetails(Long id) {
		log.info("UserService::GET_USER_DETAILS Recieved");
		Optional<User> user = userDao.findById(id);

		if (user.isPresent()) {
			return user.get();
		}

		return null;
	}

	public Optional<User> findUserUsingEmail(String userEmail) {
		log.info("UserService::FIND_USER_USING_EMAIL Recieved");
		return userDao.findUserUsingEmail(userEmail);
	}

	public List<Long> findUserFundedProjectIds(Long userId) {
		log.info("UserService::FIND_USER_FUNDED_PROJECT_IDS Recieved");
		Optional<List<Long>> resp = userDao.findUserFundedProjectIds(userId);
		return resp.isPresent() ? resp.get() : null;
	}
	
	
	public List<Long> findUserOwnedProjectIds(Long userId) {
		log.info("UserService::FIND_OWNED_PROJECT_IDS Recieved");
		Optional<List<Long>> resp = userDao.findUserOwnedProjectIds(userId);
		return resp.isPresent() ? resp.get() : null;
	}
	
	
}
