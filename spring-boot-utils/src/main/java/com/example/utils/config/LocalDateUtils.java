package com.example.utils.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author
 * @version 1.0.0
 * @deprecate 描述
 * @date 2020/2/3 15:29
 */
public class LocalDateUtils {

  private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE =
      new ConcurrentHashMap<>();

  private static final int PATTERN_CACHE_SIZE = 100;

  public static final String PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
  public static final String PATTERN_YYYYMMDD = "yyyy-MM-dd";
  public static final String PATTERN_YYYYMM = "yyyy-MM";
  public static final String PATTERN_MMDD = "MM-dd";

  /**
   * 在缓存中创建DateTimeFormatter
   *
   * @param pattern 格式
   */
  private static DateTimeFormatter createCacheFormatter(String pattern) {
    if (pattern == null || pattern.length() == 0) {
      throw new IllegalArgumentException("Invalid pattern specification");
    }
    DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
    if (formatter == null) {
      if (FORMATTER_CACHE.size() < PATTERN_CACHE_SIZE) {
        formatter = DateTimeFormatter.ofPattern(pattern);
        DateTimeFormatter oldFormatter = FORMATTER_CACHE.putIfAbsent(pattern, formatter);
        if (oldFormatter != null) {
          formatter = oldFormatter;
        }
      }
    }
    return formatter;
  }

  public static LocalDateTime dateToLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  public static LocalDate dateToLocalDate(Date date) {
    return dateToLocalDateTime(date).toLocalDate();
  }

  public static LocalTime dateToLocalTime(Date date) {
    return dateToLocalDateTime(date).toLocalTime();
  }

  public static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Date localDateToDate(LocalDate localDate) {
    Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date localTimeToDate(LocalTime localTime) {
    LocalDate localDate = LocalDate.now();
    Instant instant =
        LocalDateTime.of(localDate, localTime).atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  /**
   * localDateTime转换为格式化时间
   *
   * @param localDateTime localDateTime
   * @param pattern 格式
   */
  public static String format(LocalDateTime localDateTime, String pattern) {
    DateTimeFormatter formatter = createCacheFormatter(pattern);
    return localDateTime.format(formatter);
  }

  public static String format(LocalDate localDate, String pattern) {
    DateTimeFormatter formatter = createCacheFormatter(pattern);
    return localDate.format(formatter);
  }

  /**
   * Date转换为格式化时间
   *
   * @param date date
   * @param pattern 格式
   */
  public static String formatByLocalDateTime(Date date, String pattern) {
    return format(dateToLocalDateTime(date), pattern);
  }

  public static String formatByLocalDate(Date date, String pattern) {
    return format(dateToLocalDate(date), pattern);
  }

  /**
   * 格式化字符串转为LocalDateTime
   *
   * @param time 格式化时间
   * @param pattern 格式
   */
  public static LocalDateTime parseLocalDateTime(String time, String pattern) {
    DateTimeFormatter formatter = createCacheFormatter(pattern);
    return LocalDateTime.parse(time, formatter);
  }

  public static LocalDate parseLocalDate(String time, String pattern) {
    DateTimeFormatter formatter = createCacheFormatter(pattern);
    return LocalDate.parse(time, formatter);
  }

  /**
   * 格式化字符串转为Date
   *
   * @param time 格式化时间
   * @param pattern 格式
   */
  public static Date parseDateByLocalDateTime(String time, String pattern) {
    return localDateTimeToDate(parseLocalDateTime(time, pattern));
  }

  public static Date parseDateByLocalDate(String time, String pattern) {
    return localDateToDate(parseLocalDate(time, pattern));
  }

  public static Date getFirstDayOfMonth(String yearMonthDateStr, int monthNum) {
    YearMonth yearMonth = YearMonth.parse(yearMonthDateStr);
    LocalDate localDate =
        LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1).plusMonths(monthNum);
    return localDateToDate(localDate);
  }

  public static String getDayNameOfWeek(LocalDateTime localDateTime) {
    String[] daysOfWeek = new String[] {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    return daysOfWeek[localDateTime.getDayOfWeek().getValue() - 1];
  }

  public static String getDayNameOfWeek(Date date) {
    return getDayNameOfWeek(dateToLocalDateTime(date));
  }

  public static void main(String[] args) {
    // System.out.println(getFirstDayOfMonth("2020-01", 0));
    // System.out.println(getFirstDayOfMonth("2020-01", 1));
    // System.out.println(parseDateByLocalDate("2019-01-15",PATTERN_YYYYMMDD));
  }
}
