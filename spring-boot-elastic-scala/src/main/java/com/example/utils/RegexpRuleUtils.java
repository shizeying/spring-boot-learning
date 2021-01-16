package com.example.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 正则表达式规则工具类
 *
 * @author shizeying
 * @date 2021/01/05
 */
public final class RegexpRuleUtils {
	public static final int num = 3;
	private final static String point = ".";
	private final static String w = "\\w";
	private final static String W = "\\W";
	private final static String d = "\\d";
	private final static String D = "\\D";
	private final static String s = "\\s";
	private final static String S = "\\S";
	private final static String xt = "^";
	private final static String $ = "$";
	private final static String XIN_HAO = "*";
	private final static String wen_hao = "?";
	private final static String you_hua_kuo_hao = "{";
	private final static String zuo_hua_kuo_hao = "{";
	private final static String dou_hap = ",";
	private final static String translation = "\\.";
	private final static String zuo_fang_kuo_hao = "[";
	private final static String you_fang_hou_hap = "]";
	private final static String da_xie = "\\S+";
	
	
	public static boolean checkRegexpRule(String value) {
		if (StringUtils.isBlank(value)) {
			return false;
		} else if (value.length() < num) {
			return false;
		} else if (StringUtils.containsAny(value, point, w, W, d, D, s, S, xt, $, XIN_HAO, wen_hao, da_xie, translation, dou_hap)) {
			return true;
		} else if (StringUtils.containsAny(value, you_hua_kuo_hao, zuo_fang_kuo_hao)
				           && StringUtils.equalsAnyIgnoreCase(value, zuo_hua_kuo_hao,
				you_fang_hou_hap)) {
			return true;
		}
		
		
		return false;
		
	}
}
