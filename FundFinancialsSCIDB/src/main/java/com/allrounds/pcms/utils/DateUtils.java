package com.allrounds.pcms.utils;

import java.text.DateFormat;
import java.text.ParseException;
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
	
//	public static void main(String[] args) throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2001, 0, 1);
//		Calendar c2 = Calendar.getInstance();
//		c2.set(2000, 0, 1);
//		System.out.println(convertToDate(300, c2.getTime()));
//	}

	public static int convertToInt( Date d, Date baseDate ){
		if ( d.compareTo(baseDate) <= 0 ) {
			return 0;
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(baseDate);
		int res = 0;
		for ( int year = c2.get(Calendar.YEAR); year < c1.get(Calendar.YEAR); year++ ) {
			res += isLeapYear(year) ? 366 : 365;
		}
		//res += convertToIntOfSameYear(c1, c2);
		res += c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR) + 1;
		return res;
	}
	
	/*private static int convertToIntOfSameYear( Calendar c1, Calendar c2 ){
		int res = 0;
		boolean isLeap = isLeapYear(c1.get(Calendar.YEAR));
		int month = 0;
		for ( month = c2.get(Calendar.MONTH); month < c1.get(Calendar.MONTH); month++ ) {
			res += isLeap ? monthLengthLeap[month] : monthLength[month];
		}
		res += c1.get(Calendar.DAY_OF_YEAR) - c2.getDate();
		return res;
	}*/
	
	private static boolean isLeapYear(int year) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			return true;
		} else {
			return false;
		}
    }
	
}
