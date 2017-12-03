package com.badsocket.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Date And Time utility.
 */
public abstract class TimeUtils {

	public final static String FORMAT_YMD = "YYYY-MM-dd";

	public final static String FORMAT_YMD_HMS = "YYYY-MM-dd HH:mm:ss";


	/**
	 * Get current time in millis senconds.
	 * @return Current time in millis seconds.
	 */
	public static long millisTime() {
		return System.currentTimeMillis();
	}


	/**
	 * Get difference in two millis seconds time.
	 * @param ts First time.
	 * @param te Second time.
	 * @return Time in second of difference in two millis time.
	 */
	public static float millisTimeDiffInSec(long ts, long te) {
		return te - ts == 0L ? 0F : ((float) (te - ts)) / 1000F;
	}


	public static Date str2Date(String date, String format, Locale locale) {
		try {
			return new SimpleDateFormat(format, locale).parse(date);
		}
		catch(ParseException pe) {
			pe.printStackTrace();
		}

		return new Date();
	}


	public static Date str2Date(String date, String format) {
		return str2Date(date, format, Locale.CHINESE);
	}


	public static String getStrDateByFormat(String format, Locale locale) {
		try {
			return new SimpleDateFormat(format, locale).format(new Date());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}
