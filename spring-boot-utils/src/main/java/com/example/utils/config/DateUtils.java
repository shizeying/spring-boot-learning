package com.example.utils.config;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("ALL")
public final class DateUtils {
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_NUMBER_FORMAT = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_FOREIGN = "dd/MM/yyyy";
	public static final String DATE_TIME_FORMAT_FOREIGN = "MM/dd/yyyy HH:mm:ss";
	public static final String DATE_NUMBER_FORMAT = "yyyyMMdd";
	public static final long DAY_SECONDS = 24 * 60 * 60;
	public static final long DAY_MILLISECONDS = 24 * 60 * 60 * 1000;
	
	private DateUtils() {
	}
	
	public static String formatDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static String formatDate(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}
	
	public static String formatDate(Date date) {
		return formatDate(date, DATE_FORMAT);
	}
	
	public static String formatDatetime(Date date) {
		return formatDate(date, DATE_TIME_FORMAT);
	}
	
	public static String formatDatetime() {
		return formatDate(DATE_TIME_FORMAT);
	}
	
	
	/**
	 * 解析日期
	 *
	 * @param date    日期
	 * @param pattern 表达式
	 * @return Data
	 */
	public static Date parseDate(String date, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析日期时间
	 *
	 * @param date date
	 * @return date
	 */
	public static Date parseDatetime(String date) {
		return parseDate(date, DATE_TIME_FORMAT);
	}
	
	/**
	 * 获取两个日期相差的天数
	 *
	 * @param date1 date
	 * @param date2 date
	 * @return date
	 */
	public static int dayDiff(Date date1, Date date2) {
		Long days = (date1.getTime() - date2.getTime()) / DAY_MILLISECONDS;
		return days.intValue();
	}
	
	/**
	 * 获取一个日期与当前时间相差的天数
	 *
	 * @param date date
	 * @return date
	 */
	public static int dayDiff(Date date) {
		Calendar calendar = Calendar.getInstance();
		setTime2Start(calendar);
		return dayDiff(date, calendar.getTime());
	}
	
	/**
	 * 设置时间部分到开始时刻
	 *
	 * @param calendar s
	 */
	public static void setTime2Start(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * 设置时间部分到结束时刻
	 *
	 * @param calendar 设置时间部分到结束时刻
	 */
	public static void setTime2End(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}
	
	/**
	 * 当前年的开始时间
	 *
	 * @return date
	 */
	public static Date getFirstDayOfYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		setTime2Start(calendar);
		return calendar.getTime();
	}
	
	/**
	 * 当前年的结束时间
	 *
	 * @return date
	 */
	public static Date getLastDayOfYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		setTime2End(calendar);
		return calendar.getTime();
	}
	
	/**
	 * 当前月的开始时间
	 *
	 * @return date
	 */
	public static Date getFirstDayOfMonth() {
		return getFirstDayOfMonth(new Date());
	}
	
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		setTime2Start(calendar);
		return calendar.getTime();
	}
	
	/***
	 * 当前月的结束时间
	 * @return date
	 */
	public static Date getLastDayOfMonth() {
		return getLastDayOfMonth(new Date());
	}
	
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		setTime2End(calendar);
		return calendar.getTime();
	}
	
	/**
	 * 获取某个日期所在月的天数
	 *
	 * @param date date
	 * @return date
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days = calendar.getActualMaximum(Calendar.DATE);
		return days;
	}
	
	/**
	 * 两个时间相差的月份
	 *
	 * @param date1 date
	 * @param date2 date
	 * @return date
	 */
	public static int monthDiff(Date date1, Date date2) {
		Long month = (date1.getTime() - date2.getTime()) / (DAY_MILLISECONDS * 365 / 12);
		return month.intValue();
	}
	
	/**
	 * 获取两个日期相差的秒数
	 *
	 * @param date1 date
	 * @param date2 date
	 * @return date
	 */
	public static int minuteDiff(Date date1, Date date2) {
		Long days = (date1.getTime() - date2.getTime()) / (1000 * 60);
		return days.intValue();
	}
	
	/**
	 * 获取某个日期的月份
	 *
	 * @param date date
	 * @return date
	 */
	public static int getMonthOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}
	
	/**
	 * 获取某个日期的年份
	 *
	 * @param date date
	 * @return date
	 */
	public static int getYearOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取某个日期所在天的小时
	 *
	 * @param date date
	 * @return date
	 */
	public static int getHoursOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 日期加减
	 *
	 * @param date   date
	 * @param field  x
	 * @param amount s
	 * @return date
	 */
	public static Date add(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}
	
	/**
	 * 日期天数加减
	 *
	 * @param date   date
	 * @param amount s
	 * @return date
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}
	
	/**
	 * 日期月份加减
	 *
	 * @param date   date
	 * @param amount s
	 * @return date
	 */
	public static Date addMonths(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}
	
	/**
	 * 加减小时
	 *
	 * @param date   date
	 * @param amount s
	 * @return date
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}
	
	/**
	 * 加减分钟
	 *
	 * @param date   date
	 * @param amount s
	 * @return date
	 */
	public static Date addMinutes(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}
	
	/**
	 * 加减秒
	 *
	 * @param date   date
	 * @param amount s
	 * @return date
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}
	
	/**
	 * 获取昨天开始时间
	 *
	 * @return date
	 */
	public static Date yesterdayStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		setTime2Start(calendar);
		return calendar.getTime();
	}
	
	/**
	 * 获取昨天结束时间
	 *
	 * @return date
	 */
	public static Date yesterdayEnd() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		setTime2End(calendar);
		return calendar.getTime();
	}
	
	/**
	 * 获取今天开始时间
	 *
	 * @return date
	 */
	public static Date todayStart() {
		Calendar calendar = Calendar.getInstance();
		setTime2Start(calendar);
		return calendar.getTime();
	}
	
	/**
	 * 获取今天结束时间
	 *
	 * @return date
	 */
	public static Date todayEnd() {
		Calendar calendar = Calendar.getInstance();
		setTime2End(calendar);
		return calendar.getTime();
	}
	
	/**
	 * 获取一个时间的开始时间(00:00:00)
	 *
	 * @param timestamp 时间戳
	 * @return date
	 */
	public static Date getStartTimeOfDate(Long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		setTime2Start(calendar);
		return calendar.getTime();
	}
	
	/**
	 * 获取一个时间的结束时间(23:59:59)
	 *
	 * @param timestamp 时间戳
	 * @return date
	 */
	public static Date getEndTimeOfDate(Long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		setTime2End(calendar);
		return calendar.getTime();
	}
}

