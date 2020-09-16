package com.badsocket.util;

/**
 * Created by skyrim on 2018/1/20.
 */

public abstract class TimeCounter {

	private static long startTime;

	private static long usedTime;

	public static synchronized final void begin() {
		startTime = DateUtils.millisTime();
	}

	public static synchronized final long end() {
		usedTime = DateUtils.millisTime() - startTime;
		return usedTime();
	}

	public static synchronized final long usedTime() {
		return usedTime;
	}

	public static synchronized final void logUsedTime() {
		Log.e("-->", end() + "");
	}

}
