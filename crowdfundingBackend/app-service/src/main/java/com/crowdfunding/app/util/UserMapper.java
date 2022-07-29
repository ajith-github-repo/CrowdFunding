package com.crowdfunding.app.util;

import org.springframework.stereotype.Component;

import com.crowdfunding.app.entity.User;
import com.crowdfunding.common.dto.UserRequestDTO;
import com.crowdfunding.common.dto.UserResponseDTO;

@Component
public class UserMapper {

	public UserResponseDTO toResponseDTO(User usr) {
		
		if(usr == null) return null;
		
		UserResponseDTO user = new UserResponseDTO();
		
		user.setFirstName(usr.getFirstName());
		user.setLastName(usr.getLastName());
		user.setUserId(usr.getUserId());
	    user.setUserEmail(usr.getUserEmail());
		
		return user;
	}
	
	
	public User fromRequestDTO(UserRequestDTO userReq) {
		
		if(userReq == null) return null;
		
		User user = new User();
		
		user.setFirstName(userReq.getFirstName());
		user.setLastName(userReq.getLastName());
		user.setUserEmail(userReq.getUserEmail());
		
		return user;
	}

}
