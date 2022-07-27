package com.crowdfunding.common.util;

import java.sql.Date;

public class DateHelper {

	public static Date convertDateToSQLDate(String date) {
		return Date.valueOf(date);
	}
}
