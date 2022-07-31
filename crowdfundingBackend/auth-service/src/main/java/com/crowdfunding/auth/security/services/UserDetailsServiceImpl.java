package com.crowdfunding.auth.security.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crowdfunding.auth.entity.UserAuth;
import com.crowdfunding.auth.service.IAuthService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IAuthService authService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    	log.info("AuthService::LOAD_USER_BY_USERNAME Recieved");
    	UserAuth appUser = authService.findAuthUsingEmail(s).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
    	
    	log.info("AuthService::LOAD_USER_BY_USERNAME Returning");
    	return new User(appUser.getUserEmail(), appUser.getPassword(), new ArrayList<GrantedAuthority>());
    }
}
