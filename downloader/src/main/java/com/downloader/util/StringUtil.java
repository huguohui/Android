package com.downloader.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.regex.Pattern;

/**
 * Utility for string.
 * @since 2017/01/15 15:29
 */
final public class StringUtil {
	/** 用于匹配整数的正则。*/
	public final static Pattern mIntPattern = Pattern.compile("^-?\\d+$");
	
	/** 
	 * 日期格式。
	 */
	public static interface DateFormat {
		/** 普通格式（YYYY-MM-DD HH:mm:ss）。*/
		public final static String NORMAL = "YYYY-MM-dd HH:mm:ss";
		
		/** GMT格式 （Web, 06 Jan 2016）。*/ 
		public final static String GMT = "EEE, dd MMM yyyy HH:mm:ss z";
	};


	/**
	 * Check string if valid.
	 * @param str String will to checking.
	 * @return Valid true else fasle.
	 */
	public final static boolean isValid(String str) {
		return str != null && str.length() != 0;
	}
	
	
	/**
	 * Check a string if inArray by string array and return it's index in this array.
	 * @param arr String array.
	 * @param str String will to checking.
	 * @return >= 0 for inArray, -1 for not inArray.
	 */
	public final static int inArray(String[] arr, String str) {
		if (arr == null || arr.length == 0)
			return -1;
		
		for (int idx = 0; idx < arr.length; idx++) {
			if (arr[idx].equals(str))
				return idx;
		}
		
		return -1;
	}


	 /**
	 * Check a string if inArray by string array and return it's index in this array.
	 * @param arr String array.
	 * @param str String will to checking.
	 * @return true for inArray, false for not inArray.
	 */
	public final static boolean contains(String[] arr, String str) {
		return inArray(arr, str) != -1;
	}
	
	
	/**
	 * Check a string if inArray by string array and return it's index in this array.
	 * @param str String array.
	 * @param sch String will to searching.
	 * @return >= 0 for inArray, -1 for not inArray.
	 */
	public final static boolean contains(String str, String sch) {
		if (str == null || sch == null || sch.length() > str.length())
			return false;

		return str.indexOf(sch) !=-1;
	}
	
	
	/**
	 * 字符串转换成整数。
	 * @param str 字符串。
	 * @return 转换后的数字，或转换失败返回null。
	 */
	public final static Integer str2Int(String str) {
		return str2Int(str, null);
	}
	
	
	/**
	 * 字符串转换成整数，当转换失败时返回默认值。
	 * @param str 字符串。
	 * @param def 转换失败后返回的数字。
	 * @return 转换后的数字，或转换失败返回def。
	 */
	public final static Integer str2Int(String str, Integer def) {
		if (str == null || str.trim().length() == 0 || !isNum(str.trim()))
			return def;
		
		str = str.trim();
		try{
			return Integer.parseInt(str);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return def;
	}
	
	
	/**
	 * 字符串转换成整数。
	 * @param str 字符串。
	 * @return 转换后的数字，或转换失败返回null。
	 */
	public final static Long str2Long(String str) {
		return str2Long(str, null);
	}
	
	
	/**
	 * 字符串转换成整数，当转换失败时返回默认值。
	 * @param str 字符串。
	 * @param def 转换失败后返回的数字。
	 * @return 转换后的数字，或转换失败返回def。
	 */
	public final static Long str2Long(String str, Long def) {
		if (str == null || str.trim().length() == 0 || !isNum(str.trim()))
			return def;
		
		str = str.trim();
		try{
			return Long.parseLong(str);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return def;
	}

	
	/**
	 * 检查给定的字符串是否为整数数字。
	 * @param str 给定的字符串。
	 * @return 字符串为整数返回true,否则false。
	 */
	public final static boolean isNum(String str) {
		return mIntPattern.matcher(str).matches();
	}
	
	
	/**
	 * 将一个日期字符串转换为java日期对象。
	 * @param str 日期字符串。
	 * @return java日期对象。
	 */
	public final static Date str2Date(String str, String format) {
		if (!isValid(str))
			return null;

		try{
			return new SimpleDateFormat(format).parse(str);
		}catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * Checks given string if hex.
	 * @param hex String that need to checking.
	 * @return If is hex true else false.
	 */
	public final static boolean isHex(String hex) {
		if (hex == null || hex.length() == 0) return false;

		byte[] chars = hex.getBytes();
		for (int i = 0; i < chars.length; i++) {
			if (!((chars[i] >= 48 && chars[i] <= 57)
					|| (chars[i] >= 65 && chars[i] <= 90)
					|| (chars[i] >= 97 && chars[i] <= 122)))
				return false;
		}
		return true;
	}


	public final static String decimal2Str(Double d, int len) {
		return new java.text.DecimalFormat("0.00").format(d);
	}
}
