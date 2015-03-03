package org.pxcbpmisframework.core.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataToolsUtils {
	private static final Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat simpleformatpatten;

	public DataToolsUtils() {
	}

	public static SimpleDateFormat getSimpleformatpatten() {
		return simpleformatpatten;
	}

	public static void setSimpleformatpatten(
			SimpleDateFormat simpleformatpatten) {
		DataToolsUtils.simpleformatpatten = simpleformatpatten;
	}

	/**
	 * @Description:得到getDate
	 */
	private static Date getDate() {
		return new Date();
	}

	/**
	 * @Description:得到getTime
	 */
	public static long getTime() {
		return getDate().getTime();
	}

	/**
	 * @Description:得到getSimpleDateFormat
	 */
	public static SimpleDateFormat getDfSimpleDateFormat() {
		return simpleformatpatten = new SimpleDateFormat();
	}

	/**
	 * @Description:得到getSimpleDateFormat
	 */
	public static SimpleDateFormat getSimpleDateFormat(String patten) {
		return simpleformatpatten = new SimpleDateFormat(patten);
	}

	/**
	 * @Description:得到默认格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getSimpleDateFormat() {
		SimpleDateFormat simpleformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return simpleformat.format(getDate());
	}

	/**
	 * @Description:得到传参模式的时间 1.yyyy-MM-dd HH:mm:ss 2.yyyy-MM-dd yyyyMMdd
	 *                        3.yyyy年MM月dd日 4.yyyyMMddHHmmss
	 */
	public static String SimpleFormatTime(String patten) {
		return getSimpleDateFormat(patten).format(getDate());
	}

	/**
	 * @Description:得到年getYear
	 */
	public static int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * @Description:得到月getMonth
	 */
	public static int getMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * @Description:得到日getDay
	 */
	public static int getDay() {
		return calendar.get(Calendar.DATE);
	}

	/**
	 * @Description:得到小时getHour
	 */
	public static int getHour() {
		return calendar.get(Calendar.HOUR);
	}

	/**
	 * @Description:得到分getMinute
	 */
	public static int getMinute() {
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * @Description:得到秒getSecond
	 */
	public static int getSecond() {
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * @Description:得到上午還是下午getAM_OR_PM
	 */
	public static String getAM_OR_PM() {
		if (calendar.get(Calendar.AM_PM) == 0)
			return "上午";
		else
			return "下午";
	}

	/**
	 * @Description:得到当前年的第几周getWeek_Of_Year
	 */
	public static int getWeek_Of_Year() {
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * @Description:得到当前月的第几周getWeek_Of_Month
	 */
	public static int getWeek_Of_Month() {
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * @Description:得到当前年的第几天getDay_Of_Year
	 */
	public static int getDay_Of_Year() {
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * @Description:配置date
	 */
	private static Date parseDate(String src, String pattern) {
		ParsePosition pos = new ParsePosition(0);
		Date date = getSimpleDateFormat(pattern).parse(src, pos);
		return date;
	}

	/**
	 * @Description:String转格式化时间
	 */
	public static String parseTimeStr(String src, int length) {
		String patten = "yyyy-MM-dd HH:mm:ss";
		Date date = parseDate(src, patten);
		return getSimpleDateFormat(patten).format(date).substring(0, length);
	}

	/**
	 * @Description:long转格式化时间
	 */
	public static String longParseStr(String src) {
		return longParseStr(Long.parseLong(src));
	}

	public static String longParseStr(long src) {
		return getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(src));
	}

	/**
	 * @Description:long转格式化时间
	 */
	public static String longParseStr(String src, String pattern) {
		return getSimpleDateFormat(pattern).format(
				new Date(Long.parseLong(src)));
	}

	/**
	 * @Description:long转格式化时间
	 */
	public static String longParseStr(String src, int length) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getSimpleDateFormat(pattern).format(
				new Date(Long.parseLong(src))).substring(0, length);
	}

	public static void main(String[] args) {
		System.out.println(getYear() + " " + getMonth() + " " + getDay());

	}
}
