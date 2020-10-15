package com.badsocket.util;

import java.util.Date;

final public class Log {
	public static String LOG_FILE_NAME = "log.log";

	public static boolean isDebugMode = true;

	public final static void d(String key, String val) {
		android.util.Log.d(key, val);
	}

	public final static void d(String val) {
		android.util.Log.d("DEBUG", val);
	}

	public final static void e(String key, String val) {
		android.util.Log.e(key, val);
	}

	public final static void e(String val) {
		android.util.Log.e("ERROR", val);
	}

	public final static void e(Throwable e) {
		android.util.Log.e("", "", e);
	}

	public static String getStackTraceString(Throwable e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.getClass().getName())
				.append(": ").append(e.getMessage())
				.append("\n");
		for (StackTraceElement st : e.getStackTrace()) {
			sb.append("at: ")
					.append(st.getClassName()).append(".").append(st.getMethodName())
					.append("(").append(st.getLineNumber()).append(").")
					.append("\n");
		}

		sb.insert(0, new Date().toString() + "\n").append("\n");
		return sb.toString();
	}

	public static void debug(String obj) {
		if (!isDebugMode) {
			return;
		}

		android.util.Log.d("DEBUG", obj);
	}

	public static void setDebugMode(boolean debug) {
		isDebugMode = debug;
	}

	public static boolean isDebugMode() {
		return isDebugMode;
	}
}

