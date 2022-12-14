package com.crowdfunding.common.security;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {
   @Value("${security.jwt.uri:/auth/**}")
   private String Uri;

   @Value("${security.jwt.header:Authorization}")
   private String header;

   @Value("${security.jwt.loginUri:/auth/login}")
   private String loginUri;
   
   @Value("${security.jwt.prefix:Bearer }")
   private String prefix;

   @Value("${security.jwt.expiration:#{1*60*60}}")
   private int expiration;

   @Value("${security.jwt.secret:JwtSecretKey}")
   private String secret;
   
   
   public String getLoginUri() {
	return loginUri;
}

	public String getUri() {
		return Uri;
	}

	public String getHeader() {
		return header;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public String getSecret() {
		return secret;
	}	
   
}