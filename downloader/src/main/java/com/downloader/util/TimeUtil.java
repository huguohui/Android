package com.downloader.util;


/**
 * Date And Time utility.
 */
public abstract class TimeUtil {
	/**
	 * Get current time in millis senconds.
	 * @return Current time in millis seconds.
	 */
	public static long getMillisTime() {
		return System.currentTimeMillis();
	}


	/**
	 * Get difference in two millis seconds time.
	 * @param ts First time.
	 * @param te Second time.
	 * @return Time in second of difference in two millis time.
	 */
	public static float getMillisTimeDiffInSec(long ts, long te) {
		return te - ts == 0L ? 0F : ((float) (te - ts)) / 1000F;
	}
}
