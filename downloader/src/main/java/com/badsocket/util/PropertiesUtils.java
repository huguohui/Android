package com.badsocket.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by skyrim on 2017/12/16.
 */

public abstract class PropertiesUtils {

	public static Properties load(File file) {
		try {
			return load(new BufferedInputStream(new FileInputStream(file)));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return new Properties();
	}


	public static Properties load(InputStream stream) {
		Properties p = new Properties();
		try {
			p.load(stream);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return p;
	}

}
