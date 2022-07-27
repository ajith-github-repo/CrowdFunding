
package com.crowdfunding.common.util;


import com.crowdfunding.common.exceptions.RequestNotProperException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JWTHelper {

	public static String getCurrentlyLoggedInUserFromJWT(String jwtToken,String jwtSecret) {

		try {

			Claims claims = Jwts.parser()
					.setSigningKey(jwtSecret.getBytes())
					.parseClaimsJws(jwtToken)
					.getBody();
			
			String username = claims.getSubject();

			return username;
		} catch (SignatureException ex) {
			 throw new RequestNotProperException();
		}

	}
}