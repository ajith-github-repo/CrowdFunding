package com.crowdfunding.common.constants;

import java.util.regex.Pattern;

public class Constants {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public static final Pattern VALID_PASSWORD_REGEX = 
			Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", Pattern.CASE_INSENSITIVE);
	
	public static final String SALT = "CROWD_FUNDING";
	
	public static final String JWT_SIGNING_SECRET = "zkcdJFS34wfsdfsdfSDSD32dfsddLLerQSNMK34SOWEK5354fdgdf4";
}
