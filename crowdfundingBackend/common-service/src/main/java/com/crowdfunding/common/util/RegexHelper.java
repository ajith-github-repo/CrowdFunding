package com.crowdfunding.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

	public static boolean validate(String emailStr,Pattern pattern) {
		        Matcher matcher = pattern.matcher(emailStr);
		        return matcher.find();
	}
}
