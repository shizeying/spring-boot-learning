package com.example.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.matches;

/**
 * regexp跑龙套
 *
 * @author shizeying
 * @date 2021/01/05
 */
public final class RegexpUtils {
	/** 小数 */
	public static final String DECIMALS = "\\-?\\d+(\\.\\d+)?";
	
	public final static String DATE_FORMAT = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|" +
			                                         "([1-2" +
			                                         "][0-9])" +
			                                         "|(3[01])))|" +
			                                         "((" +
			                                         "(0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|" +
			                                         "([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|" +
			                                         "(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?(" +
			                                         "(0?[1-9])" +
			                                         "|" +
			                                         "([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	public static final String URL = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
	public static final String POSTCODE = "[1-9]\\d{5}";
	public static final String IPADDRESS = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
	/**
	 * 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号: 数学减号- 右尖括号> 左尖括号< 反斜杠/
	 * 即空格,制表符,回车符等 )
	 * <p>
	 * 格式为: x 或 一个一上的字符
	 * <p>
	 * 匹配 : 012345
	 * <p>
	 * 不匹配: 0123456 // ;,:-<>//s].+$";//
	 */
	public static final String NON_SPECIAL_CHAR_REGEXP = "^[^'/";
	public static final String ID_CARD = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
	public static final String CHINESE_AND_ENGLISH = "[\\u4e00-\\u9fa5_a-zA-Z0-9]+";
	/**
	 * 数字
	 */
	public static String NUMBERS = "[0-9]*";
	/**
	 * 中文
	 */
	public static String CHINESE = "[\u4e00-\u9fa5]{0,}";
	/**
	 * email
	 */
	public static String EMAIL = "\\w+([-+.]\\w+)*@\\w+([-+.]\\w+)*\\.\\w+([-+.]\\w+)*?";
	/**
	 * 域名
	 */
	public static String DOMAIN_NAME = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?";
	/**
	 * InternetURL
	 */
	public static String URL2 = "[a-zA-z]+://[^\\s]*";
	/**
	 * 手机号码
	 */
	public static String mobile_no = "([1][3,4,5,6,7,8,9])\\d{9}";
	/**
	 * 电话号码
	 */
	public static String phone_number = "(\\(\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}";
	/**
	 * 国内电话号码
	 */
	public static String home_phone_number = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";
	/**
	 * 18位身份证号码(数字、字母x结尾)
	 */
	public static String id_card_no = "((\\d{18})|([0-9x]{18})|([0-9X]{18}))";
	/**
	 * 日期格式
	 */
	public static String DATE_FORMAT_HH_MM_SS = "\\d{4}[-.]\\d{1,2}[-.]\\d{1,2}(\\s\\d{2}:\\d{2}(:\\d{2})?)?";
	/** 国际手机号正则表达式 */
	private static Map<String, String> INTL_MOBILE_REGEX = new HashMap<String, String>();
	
	static {
		INTL_MOBILE_REGEX.put("ar-DZ", "^(\\+?213|0)(5|6|7)\\d{8}$");
		INTL_MOBILE_REGEX.put("ar-SY", "^(!?(\\+?963)|0)?9\\d{8}$");
		INTL_MOBILE_REGEX.put("ar-SA", "^(!?(\\+?966)|0)?5\\d{8}$");
		INTL_MOBILE_REGEX.put("en-US", "^(\\+?1)?[2-9]\\d{2}[2-9](?!11)\\d{6}$");
		INTL_MOBILE_REGEX.put("cs-CZ", "^(\\+?420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$");
		INTL_MOBILE_REGEX.put("de-DE", "^(\\+?49[ \\.\\-])?([\\(]{1}[0-9]{1,6}[\\)])?([0-9 \\.\\-\\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$");
		INTL_MOBILE_REGEX.put("da-DK", "^(\\+?45)?(\\d{8})$");
		INTL_MOBILE_REGEX.put("el-GR", "^(\\+?30)?(69\\d{8})$");
		INTL_MOBILE_REGEX.put("en-AU", "^(\\+?61|0)4\\d{8}$");
		INTL_MOBILE_REGEX.put("en-GB", "^(\\+?44|0)7\\d{9}$");
		INTL_MOBILE_REGEX.put("en-HK", "^(\\+?852\\-?)?[569]\\d{3}\\-?\\d{4}$");
		INTL_MOBILE_REGEX.put("en-IN", "^(\\+?91|0)?[789]\\d{9}$");
		INTL_MOBILE_REGEX.put("en-NZ", "^(\\+?64|0)2\\d{7,9}$");
		INTL_MOBILE_REGEX.put("en-ZA", "^(\\+?27|0)\\d{9}$");
		INTL_MOBILE_REGEX.put("en-ZM", "^(\\+?26)?09[567]\\d{7}$");
		INTL_MOBILE_REGEX.put("es-ES", "^(\\+?34)?(6\\d{1}|7[1234])\\d{7}$");
		INTL_MOBILE_REGEX.put("fi-FI", "^(\\+?358|0)\\s?(4(0|1|2|4|5)?|50)\\s?(\\d\\s?){4,8}\\d$");
		INTL_MOBILE_REGEX.put("fr-FR", "^(\\+?33|0)[67]\\d{8}$");
		INTL_MOBILE_REGEX.put("he-IL", "^(\\+972|0)([23489]|5[0248]|77)[1-9]\\d{6}$");
		INTL_MOBILE_REGEX.put("hu-HU", "^(\\+?36)(20|30|70)\\d{7}$");
		INTL_MOBILE_REGEX.put("it-IT", "^(\\+?39)?\\s?3\\d{2} ?\\d{6,7}$");
		INTL_MOBILE_REGEX.put("ja-JP", "^(\\+?81|0)\\d{1,4}[ \\-]?\\d{1,4}[ \\-]?\\d{4}$");
		INTL_MOBILE_REGEX.put("ms-MY", "^(\\+?6?01){1}(([145]{1}(\\-|\\s)?\\d{7,8})|([236789]{1}(\\s|\\-)?\\d{7}))$");
		INTL_MOBILE_REGEX.put("nb-NO", "^(\\+?47)?[49]\\d{7}$");
		INTL_MOBILE_REGEX.put("nl-BE", "^(\\+?32|0)4?\\d{8}$");
		INTL_MOBILE_REGEX.put("nn-NO", "^(\\+?47)?[49]\\d{7}$");
		INTL_MOBILE_REGEX.put("pl-PL", "^(\\+?48)? ?[5-8]\\d ?\\d{3} ?\\d{2} ?\\d{2}$");
		INTL_MOBILE_REGEX.put("pt-BR", "^(\\+?55|0)\\-?[1-9]{2}\\-?[2-9]{1}\\d{3,4}\\-?\\d{4}$");
		INTL_MOBILE_REGEX.put("pt-PT", "^(\\+?351)?9[1236]\\d{7}$");
		INTL_MOBILE_REGEX.put("ru-RU", "^(\\+?7|8)?9\\d{9}$");
		INTL_MOBILE_REGEX.put("sr-RS", "^(\\+3816|06)[- \\d]{5,9}$");
		INTL_MOBILE_REGEX.put("tr-TR", "^(\\+?90|0)?5\\d{9}$");
		INTL_MOBILE_REGEX.put("vi-VN", "^(\\+?84|0)?((1(2([0-9])|6([2-9])|88|99))|(9((?!5)[0-9])))([0-9]{7})$");
		INTL_MOBILE_REGEX.put("zh-CN", "^(\\+?0?86\\-?)?1[345789]\\d{9}$");
		INTL_MOBILE_REGEX.put("zh-TW", "^(\\+?886\\-?|0)?9\\d{8}$");
	}
	
	/**
	 * 验证Email
	 *
	 * @param email
	 * 		email地址，格式：zhangsan@163.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkEmail(String email) {
		return matches(EMAIL, email);
	}
	
	public static String getEmail(String email) {
		Matcher matcher = compile(EMAIL).matcher(email);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	
	/**
	 * 验证身份证号码
	 *
	 * @param idCard
	 * 		居民身份证号码15位或18位，最后一位可能是数字或字母
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIdCard(String idCard) {
		return matches(ID_CARD, idCard);
	}
	
	public static String getIdCard(String idCard) {
		Matcher matcher = compile(ID_CARD).matcher(idCard);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	
	/**
	 * 验证国际手机号码(支持国内手机号)：
	 * <pre>
	 * ar-DZ: /^(\+?213|0)(5|6|7)\d{8}$/,
	 * ar-SY: /^(!?(\+?963)|0)?9\d{8}$/,
	 * ar-SA: /^(!?(\+?966)|0)?5\d{8}$/,
	 * en-US: /^(\+?1)?[2-9]\d{2}[2-9](?!11)\d{6}$/,
	 * cs-CZ: /^(\+?420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$/,
	 * de-DE: /^(\+?49[ \.\-])?([\(]{1}[0-9]{1,6}[\)])?([0-9 \.\-\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$/,
	 * da-DK: /^(\+?45)?(\d{8})$/,
	 * el-GR: /^(\+?30)?(69\d{8})$/,
	 * en-AU: /^(\+?61|0)4\d{8}$/,
	 * en-GB: /^(\+?44|0)7\d{9}$/,
	 * en-HK: /^(\+?852\-?)?[569]\d{3}\-?\d{4}$/,
	 * en-IN: /^(\+?91|0)?[789]\d{9}$/,
	 * en-NZ: /^(\+?64|0)2\d{7,9}$/,
	 * en-ZA: /^(\+?27|0)\d{9}$/,
	 * en-ZM: /^(\+?26)?09[567]\d{7}$/,
	 * es-ES: /^(\+?34)?(6\d{1}|7[1234])\d{7}$/,
	 * fi-FI: /^(\+?358|0)\s?(4(0|1|2|4|5)?|50)\s?(\d\s?){4,8}\d$/,
	 * fr-FR: /^(\+?33|0)[67]\d{8}$/,
	 * he-IL: /^(\+972|0)([23489]|5[0248]|77)[1-9]\d{6}/,
	 * hu-HU: /^(\+?36)(20|30|70)\d{7}$/,
	 * it-IT: /^(\+?39)?\s?3\d{2} ?\d{6,7}$/,
	 * ja-JP: /^(\+?81|0)\d{1,4}[ \-]?\d{1,4}[ \-]?\d{4}$/,
	 * ms-MY: /^(\+?6?01){1}(([145]{1}(\-|\s)?\d{7,8})|([236789]{1}(\s|\-)?\d{7}))$/,
	 * nb-NO: /^(\+?47)?[49]\d{7}$/,
	 * nl-BE: /^(\+?32|0)4?\d{8}$/,
	 * nn-NO: /^(\+?47)?[49]\d{7}$/,
	 * pl-PL: /^(\+?48)? ?[5-8]\d ?\d{3} ?\d{2} ?\d{2}$/,
	 * pt-BR: /^(\+?55|0)\-?[1-9]{2}\-?[2-9]{1}\d{3,4}\-?\d{4}$/,
	 * pt-PT: /^(\+?351)?9[1236]\d{7}$/,
	 * ru-RU: /^(\+?7|8)?9\d{9}$/,
	 * sr-RS: /^(\+3816|06)[- \d]{5,9}$/,
	 * tr-TR: /^(\+?90|0)?5\d{9}$/,
	 * vi-VN: /^(\+?84|0)?((1(2([0-9])|6([2-9])|88|99))|(9((?!5)[0-9])))([0-9]{7})$/,
	 * zh-CN: /^(\+?0?86\-?)?1[345789]\d{9}$/,
	 * zh-TW: /^(\+?886\-?|0)?9\d{8}$/
	 * </pre>
	 *
	 * @param mobile
	 * 		国内和国际手机号
	 *
	 * @return 验证成功返回true; 验证失败返回false
	 */
	public static boolean checkIntlMobile(String mobile) {
		
		for (String regex : INTL_MOBILE_REGEX.values()) {
			if (matches(regex, mobile)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 手机号
	 *
	 * @param mobile
	 * 		移动
	 *
	 * @return {@link String}
	 */
	public static String getIntlMobile(String mobile) {
		for (String regex : INTL_MOBILE_REGEX.values()) {
			
			Matcher matcher = compile(regex).matcher(mobile);
			if (matcher.find()) {
				return matcher.group();
			}
		}
		return null;
	}
	
	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数
	 * 17+除9的任意数 147
	 */
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 验证固定电话号码
	 *
	 * @param phone
	 * 		电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
	 * 		<p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
	 * 		数字之后是空格分隔的国家（地区）代码。</p>
	 * 		<p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
	 * 		对不使用地区或城市代码的国家（地区），则省略该组件。</p>
	 * 		<p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPhone(String phone) {
		String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return matches(regex, phone);
	}
	
	/**
	 * 验证整数（正整数和负整数）
	 *
	 * @param digit
	 * 		一位或多位0-9之间的整数
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDigit(String digit) {
		return matches(NUMBERS, digit);
	}
	public static String  getDigit(String digit) {
		Matcher matcher = compile(NUMBERS).matcher(digit);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	/**
	 * 验证整数和浮点数（正负整数和正负浮点数）
	 *
	 * @param decimals
	 * 		一位或多位0-9之间的浮点数，如：1.23，233.30
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDecimals(String decimals) {
		return matches(DECIMALS, decimals);
	}
	
	/**
	 * 一位或多位0-9之间的浮点数，如：1.23，233.30
	 *
	 * @param decimals
	 * 		小数
	 *
	 * @return {@link String}
	 */
	public static String getDecimals(String decimals) {
		Matcher matcher = compile(DECIMALS).matcher(decimals);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	
	/**
	 * 验证空白字符
	 *
	 * @param blankSpace
	 * 		空白字符，包括：空格、\t、\n、\r、\f、\x0B
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBlankSpace(String blankSpace) {
		String regex = "\\s+";
		return matches(regex, blankSpace);
	}
	
	/**
	 * 验证中文
	 *
	 * @param chinese
	 * 		中文字符
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkChinese(String chinese) {
		String regex = "[\\u4E00-\\u9FA5]+";
		return matches(regex, chinese);
	}
	
	/**
	 * 验证日期（年月日）
	 *
	 * @param birthday
	 * 		日期，格式：1992-09-03，或1992.09.03
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBirthday(String birthday) {
		return matches(DATE_FORMAT, birthday);
	}
	
	public static boolean checkDate(String date) {
		/**
		 * 判断日期格式和范围
		 */
		return matches(DATE_FORMAT, date) || matches(DATE_FORMAT_HH_MM_SS, date) || matches(DATE_FORMAT, date);
	}
	
	public static String getIsDate(String date) {
		Matcher matcher1 = compile(DATE_FORMAT).matcher(date);
		Matcher matcher2 = compile(DATE_FORMAT_HH_MM_SS).matcher(date);
		if (matcher1.find()) {
			return matcher1.group();
		} else if (matcher2.find()) {
			return matcher2.group();
		}
		return null;
	}
	
	/**
	 * 验证URL地址
	 *
	 * @param url
	 * 		格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkURL(String url) {
		return matches(URL, url);
	}
	
	public static String getURL(String url) {
		Matcher matcher = compile(URL).matcher(url);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	
	/**
	 * <pre>
	 * 获取网址 URL 的一级域
	 * </pre>
	 *
	 * @param url
	 *
	 * @return
	 */
	public static String getDomain(String url) {
		Pattern p = compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
		// 获取完整的域名
		Matcher matcher = p.matcher(url);
		matcher.find();
		return matcher.group();
	}
	
	/**
	 * 匹配中国邮政编码
	 *
	 * @param postcode
	 * 		邮政编码
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPostcode(String postcode) {
		return matches(POSTCODE, postcode);
	}
	
	public static String getPostcode(String postcode) {
		Matcher matcher = compile(POSTCODE).matcher(postcode);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	
	/**
	 * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
	 *
	 * @param ipAddress
	 * 		IPv4标准地址
	 *
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIpAddress(String ipAddress) {
		return matches(IPADDRESS, ipAddress);
	}
	
	public static String getIpAddress(String ipAddress) {
		Matcher matcher = compile(IPADDRESS).matcher(ipAddress);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	
	/**
	 * 检查中文和英文
	 *
	 * @param ipAddress
	 * 		ip地址
	 *
	 * @return boolean
	 */
	public static boolean checkChineseAndEnglish(String ipAddress) {
		return matches(CHINESE_AND_ENGLISH, ipAddress);
	}
	
	/**
	 * @param str
	 * 		被匹配的字符串
	 * @param regEx
	 * 		正则表达式
	 *
	 * @return 是否匹配成功
	 */
	static boolean regCheck(String str, String regEx) {
		if (Objects.nonNull(StringUtils.trimToNull(str))) {
			return false;
		}
		// 编译正则表达式
		Pattern pattern = compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 字符串是否与正则表达式相匹配
		return matcher.matches();
	}
	
}
