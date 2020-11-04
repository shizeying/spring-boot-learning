package com.example.utils.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public final class Md5Utils {
	public static final String CHARSET = "UTF-8";
	public static final String MD5 = "MD5";
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f'};
	
	private Md5Utils() {
	}
	
	
	public static String encrypt(byte[] bytes) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(MD5);
			messageDigest.update(bytes);
		} catch (Exception e) {
			return null;
		}
		byte[] updateBytes = messageDigest.digest();
		int len = updateBytes.length;
		char[] newChar = new char[len * 2];
		int k = 0;
		for (int i = 0; i < len; i++) {
			byte byte0 = updateBytes[i];
			newChar[(k++)] = HEX_DIGITS[(byte0 >>> 4 & 0xF)];
			newChar[(k++)] = HEX_DIGITS[(byte0 & 0xF)];
		}
		return new String(newChar);
	}
	
	public static String encrypt(String plainText) {
		try {
			return encrypt(plainText.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}

