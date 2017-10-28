package com.xstream.util;

/**
 * Created by skyrim on 2017/10/15.
 */

public abstract class ComputeUtils {

	public final static String[] STORAGE_UNITS = {
		"Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB", "BB"
	};


	public final static String getFriendlyUnitOfBytes(long bytes, int bit) {
		int radix = 1024;
		double val = bytes;
		int i = 0;

		while (val >= 1024) {
			val /= (double) radix;
			i++;
		}

		return StringUtil.decimal2Str(val, 2) + STORAGE_UNITS[i];
	}

}
