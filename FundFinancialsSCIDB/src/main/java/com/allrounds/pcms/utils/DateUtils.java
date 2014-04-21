package com.allrounds.pcms.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	private static int[] monthLength = {31,28,31,30,31,30,31,31,30,31,30,31};
	private static int[] monthLengthLeap = {31,29,31,30,31,30,31,31,30,31,30,31};
	
	public static Date convertToDate( int d, Date baseDate ){
		if ( d < 0 ) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(baseDate);
		c.add(Calendar.DATE, d);
		return c.getTime();
	}

	public static int convertToInt( Date d, Date baseDate ){
		if ( d.compareTo(baseDate) <= 0 ) {
			return 0;
		}
		int res = 0;
		for ( int year = baseDate.getYear(); year < d.getYear(); year++ ) {
			res += isLeapYear(year) ? 366 : 365;
		}
		res += convertToIntOfSameYear(d, baseDate);
		return res;
	}
	
	private static int convertToIntOfSameYear( Date d, Date baseDate ){
		int res = 0;
		boolean isLeap = isLeapYear(d.getYear());
		int month = 0;
		for ( month = baseDate.getMonth(); month < d.getMonth(); month++ ) {
			res += isLeap ? monthLengthLeap[month] : monthLength[month];
		}
		res += d.getDate() - baseDate.getDate();
		return res;
	}
	
	private static boolean isLeapYear(int year) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			return true;
		} else {
			return false;
		}
    }
	
}
