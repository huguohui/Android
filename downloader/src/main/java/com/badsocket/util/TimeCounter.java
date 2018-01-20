package com.badsocket.util;

/**
 * Created by skyrim on 2018/1/20.
 */

public abstract class TimeCounter {

	private static long startTime;

	private static long usedTime;


	public static final void begin() {
		startTime = DateUtils.millisTime();
	}


	public static final long end() {
		usedTime = DateUtils.millisTime() - startTime;
		return usedTime();
	}


	public static final long usedTime() {
		return usedTime;
	}


}
