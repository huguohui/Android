package com.tankwar.utils;

import android.os.Environment;

import com.tankwar.engine.GameContext;

import java.io.*;
import java.util.Date;


final public class Log
{
	public static String LOG_FILE_NAME = "game.log";
	public static String LOG_FILE_PATH = GameContext.SDCARD_APP_ROOT + GameContext.DS +
            GameContext.LOG_DIR + GameContext.DS + LOG_FILE_NAME;

	public final synchronized static void e(Throwable e) {
        FileWriter fw = null;
		try {
            fw = new FileWriter(LOG_FILE_PATH, true);
			File file = new File(LOG_FILE_PATH);
			String date = new Date().toString();
			String eMsg = null;

            if (file.length() > 1024 * 100) {
				file.delete();
				file.createNewFile();
			}

			eMsg = "Messages: " + e.getMessage() + "\nException: " + e.getClass() + "\n";
			for (StackTraceElement st : e.getStackTrace()) {
				eMsg += "\tFile: " + st.getFileName() + "\n\t\t" + "Class\t: " + st.getClassName() + "\n\t\t" +
						"Line\t: " + st.getLineNumber() + "\n\t\t" + "Method\t: " + st.getMethodName() + "\n";
			}

			eMsg = date + "\n" + eMsg + "\n";
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
}


