package com.crowdfunding.auth.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.crowdfunding.common.constants.Constants;


public class Encoder implements PasswordEncoder{

	@Override
	public String encode(CharSequence rawPassword) {
		// TODO Auto-generated method stub
		
		 String generatedPassword = null;
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            md.update(Constants.SALT.getBytes());
	            byte[] bytes = md.digest(rawPassword.toString().getBytes());
	            StringBuilder sb = new StringBuilder();
	            for (int i = 0; i < bytes.length; i++) {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            generatedPassword = sb.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return generatedPassword;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		String actualEncoded = encode(rawPassword);	
		return actualEncoded != null && actualEncoded.equals(encodedPassword) ? true : false;
	}
	
}
