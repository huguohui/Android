package com.downloader.util;


public abstract class DateUtil {
	public static long getMillisTime() {
		return System.currentTimeMillis();
	}


	public static float getMillisTimeDiffInSec(long ts, long te) {
		return te - ts == 0L ? 0F : ((float) (te - ts)) / 1000F;
	}
}
