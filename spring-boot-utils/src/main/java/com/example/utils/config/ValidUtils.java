package com.example.utils.config;


import com.example.utils.constant.RegexConstants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public final class ValidUtils {
	
	public static boolean doRegexValid(String regex, String src) {
		if (isBlank(regex) || isBlank(src)) {
			return false;
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(src);
		return m.matches();
	}
	
	private static boolean isBlank(String str) {
		return str == null || str.isEmpty();
	}
	
	public static boolean isEmail(String email) {
		return doRegexValid(RegexConstants.EMAIL, email);
	}
	
	public static boolean isNumber(String number) {
		return doRegexValid(RegexConstants.NUMBER, number);
	}
	
	public static boolean isInteger(String integer) {
		return doRegexValid(RegexConstants.INTEGER, integer);
	}
	
	public static boolean isDouble(String dbl) {
		return doRegexValid(RegexConstants.DOUBLE, dbl);
	}
	
	public static boolean isUrl(String url) {
		return doRegexValid(RegexConstants.SIMPLE_URL, url);
	}
	
	public static boolean isWordNum(String wordNum) {
		return doRegexValid(RegexConstants.WORD_NUM, wordNum);
	}
	
	public static boolean isDate(String date) {
		return doRegexValid(RegexConstants.DATE, date);
	}
	
	public static boolean isDatetime(String date) {
		return doRegexValid(RegexConstants.DATETIME, date);
	}
}

