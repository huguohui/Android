package com.downloader.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Utility for string.
 * @since 2017/01/15 15:29
 */
final public class StringUtil {
	/** ����ƥ������������*/
	public final static Pattern mIntPattern = Pattern.compile("^-?\\d+$");
	
	/** 
	 * ���ڸ�ʽ��
	 */
	public static interface DateFormat {
		/** ��ͨ��ʽ��YYYY-MM-DD HH:mm:ss����*/
		public final static String NORMAL = "YYYY-MM-dd HH:mm:ss";
		
		/** GMT��ʽ ��Web, 06 Jan 2016����*/ 
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
	 * Check a string if contains by string array and return it's index in this array.
	 * @param arr String array.
	 * @param str String will to checking.
	 * @return >= 0 for contains, -1 for not contains.
	 */
	public final static int contains(String[] arr, String str) {
		if (arr == null || arr.length == 0)
			return -1;
		
		for (int idx = 0; idx < arr.length; idx++) {
			if (arr[idx].equals(str))
				return idx;
		}
		
		return -1;
	}
	
	
	/**
	 * Check a string if contains by string array and return it's index in this array.
	 * @param arr String array.
	 * @param str String will to checking.
	 * @return >= 0 for contains, -1 for not contains.
	 */
	public final static int contains(String s, String str) {
		
		return -1;
	}
	
	
	/**
	 * �ַ���ת����������
	 * @param str �ַ�����
	 * @return ת��������֣���ת��ʧ�ܷ���null��
	 */
	public final static Integer str2Int(String str) {
		return str2Int(str, null);
	}
	
	
	/**
	 * �ַ���ת������������ת��ʧ��ʱ����Ĭ��ֵ��
	 * @param str �ַ�����
	 * @param def ת��ʧ�ܺ󷵻ص����֡�
	 * @return ת��������֣���ת��ʧ�ܷ���def��
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
	 * �ַ���ת����������
	 * @param str �ַ�����
	 * @return ת��������֣���ת��ʧ�ܷ���null��
	 */
	public final static Long str2Long(String str) {
		return str2Long(str, null);
	}
	
	
	/**
	 * �ַ���ת������������ת��ʧ��ʱ����Ĭ��ֵ��
	 * @param str �ַ�����
	 * @param def ת��ʧ�ܺ󷵻ص����֡�
	 * @return ת��������֣���ת��ʧ�ܷ���def��
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
	 * ���������ַ����Ƿ�Ϊ�������֡�
	 * @param str �������ַ�����
	 * @return �ַ���Ϊ��������true,����false��
	 */
	public final static boolean isNum(String str) {
		return mIntPattern.matcher(str).matches();
	}
	
	
	/**
	 * ��һ�������ַ���ת��Ϊjava���ڶ���
	 * @param str �����ַ�����
	 * @return java���ڶ���
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
}
