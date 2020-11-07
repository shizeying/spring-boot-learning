package com.example.utils.config;

import io.vavr.control.Try;
import java.security.MessageDigest;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("ALL")
@Slf4j
public final class Md5Utils {
	
	public static final String CHARSET = "UTF-8";
	public static final String MD5 = "MD5";
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
			'b', 'c', 'd',
			'e', 'f'};
	
	private Md5Utils() {
	}
	
	
	public static String encrypt(byte[] bytes) {
		
		MessageDigest messageDigest = Try
				.of(() -> MessageDigest.getInstance(MD5))
				.onFailure(error ->
						log.error("encrypt is error:[{}]", error.getCause())
				)
				.get();
		messageDigest.update(bytes);
		
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
		return Try.of(() -> encrypt(plainText.getBytes(CHARSET)))
				.onFailure(error ->
						log.error("encrypt is error:[{}]", error.getCause())
				).get();
		
	}
}

