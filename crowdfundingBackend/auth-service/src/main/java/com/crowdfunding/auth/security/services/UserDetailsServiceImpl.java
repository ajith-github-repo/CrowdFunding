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
import com.crowdfunding.auth.service.AuthService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        
    	UserAuth appUser = authService.findAuthUsingEmail(s).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
    	
    	return new User(appUser.getUserEmail(), appUser.getPassword(), new ArrayList<GrantedAuthority>());
    }
}
