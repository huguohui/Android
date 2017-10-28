package com.badsocket.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


final public class Log
{
	public static String LOG_FILE_NAME = "game.log";
	public static String LOG_FILE_PATH = "/sdcard/" + LOG_FILE_NAME;

	public final synchronized static void eF(Throwable e) {
		println(e.toString());
/*
        SimpleFileWriter fw = null;
		try {
            fw = new SimpleFileWriter(LOG_FILE_PATH, true);
			File file = new File(LOG_FILE_PATH);
			String date = new Date().toString();
			String eMsg = null;

            if (file.length() > 1024 * 100) {
				file.delete();
				file.createNewFile();
			}


			fw.append(eMsg);
			fw.flush();
			fw.close();
		} catch (IOException err) {
            android.util.Log.e("error", err.getMessage());
		}finally{
            if (fw != null) {
                try {
                    fw.flush();
                    fw.close();
                } catch (IOException e1) {
                    android.util.Log.e("error", e1.getMessage());
                }
            }
        }
*/
	}


	public final synchronized static void s(String s) {
		try {
			FileWriter fw = new FileWriter(LOG_FILE_PATH, true);
			File f = new File(LOG_FILE_PATH);
			String date = new Date().toString();

			if (f.length() > 1024 * 100) {
				f.delete();
				f.createNewFile();
			}

			fw.append(date + " Msg: " + s + "\n");
			fw.flush();
			fw.close();
		} catch (IOException err) {
            android.util.Log.e("error", err.getMessage());
		}
	}


	public final static void debug(String key, String val) {
		android.util.Log.d(key, val);
	}



	public final static void error(String key, String val) {
		android.util.Log.e(key, val);
	}


	public final static void e(Throwable e) {
		android.util.Log.e("StrackTrace", getStackTraceString(e));
	}


	public static String getStackTraceString(Throwable e) {
		StringBuilder sb = new StringBuilder();
		sb.append("Messages: ").append(e.getMessage())
				.append("\nException: ").append(e.getClass()).append("\n");
		for (StackTraceElement st : e.getStackTrace()) {
			sb.append("\tFile: ").append(st.getFileName()).append("\n\t\t")
					.append("Class\t: ").append(st.getClassName()).append("\n\t\t")
					.append("Line\t: ").append(st.getLineNumber()).append("\n\t\t")
					.append("Method\t: ").append(st.getMethodName()).append("\n");
		}

		sb.insert(0, new Date().toString() + "\n").append("\n");
		return sb.toString();
	}


	public static void println(Object obj) {
		System.out.println("[**] " + obj);
		//android.util.Log.e("ERROR", obj.toString());
	}


	public static void print(Object obj) {
		System.out.print("[**] " + obj + "\t");
		//android.util.Log.e("ERROR", obj.toString());
	}
}


