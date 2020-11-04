package com.example.utils.config;


import java.util.Random;

public final class RadixUtils {
	private static final int CHAR_RADIX = 36;
	private static final char[] CHAR_RADIX_ELEMENTS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private static final char CHAR_COMPLETE_ELEMENT = '0';
	private static final int CHAR_ENCODE_LENGTH = 8;
	
	private RadixUtils() {
	}
	
	private static String encode(int radix, char[] radixElements, char completeElement, long id, int encodeLength) {
		char[] buf = new char[radix];
		int charPos = radix;
		while ((id / radixElements.length) > 0) {
			int ind = (int) (id % radixElements.length);
			buf[--charPos] = radixElements[ind];
			id /= radixElements.length;
		}
		buf[--charPos] = radixElements[(int) (id % radixElements.length)];
		String str = new String(buf, charPos, (radix - charPos));
		if (str.length() < encodeLength) {
			StringBuilder sb = new StringBuilder();
			sb.append(completeElement);
			Random rnd = new Random();
			for (int i = 1; i < encodeLength - str.length(); i++) {
				sb.append(radixElements[rnd.nextInt(radixElements.length)]);
			}
			str += sb.toString();
		}
		return str;
	}
	
	private static long decode(char[] radixElements, char completeElement, String code) {
		char[] chs = code.toCharArray();
		long res = 0;
		for (int i = 0; i < chs.length; i++) {
			int ind = 0;
			for (int j = 0; j < radixElements.length; j++) {
				if (chs[i] == radixElements[j]) {
					ind = j;
					break;
				}
			}
			if (chs[i] == completeElement) {
				break;
			}
			if (i > 0) {
				res = res * radixElements.length + ind;
			} else {
				res = ind;
			}
		}
		return res;
	}
	
	public static String encode(long id) {
		if (id <= 0) {
			throw new IllegalArgumentException("进制编码参数id必须大于0");
		}
		return encode(CHAR_RADIX, CHAR_RADIX_ELEMENTS, CHAR_COMPLETE_ELEMENT, id, CHAR_ENCODE_LENGTH);
	}
	
	public static long decode(String code) {
		return decode(CHAR_RADIX_ELEMENTS, CHAR_COMPLETE_ELEMENT, code);
	}
}

