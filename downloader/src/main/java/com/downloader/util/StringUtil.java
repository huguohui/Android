package com.downloader.util;

/**
 * Utility for string.
 * @since 2017/01/15 15:29
 */
final public class StringUtil {
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
}
