package com.badsocket.core.config;

import com.badsocket.util.PropertiesUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Created by skyrim on 2017/12/16.
 */

public class PropertiesConfigReader implements ConfigReader {

	private File configFile;

	private InputStream inputStream;

	public PropertiesConfigReader() {
	}

	public PropertiesConfigReader(File file) {
		configFile = file;
	}

	public PropertiesConfigReader(InputStream stream) {
		inputStream = stream;
	}

	@Override
	public Config read() throws IOException {
		Config config = new DownloadConfig(configFile);
		if (configFile != null) {
			config = read(configFile);
		}
		else if (inputStream != null) {
			config = read(inputStream);
		}

		return config;
	}

	@Override
	public Config read(File file) throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("The argument is null!");
		}
		if (!file.exists() || !file.canRead() || file.isDirectory()) {
			throw new IOException("Config file: " + file.getAbsolutePath() + " is invalid!");
		}

		Config config = new DownloadConfig(file);
		Properties properties = PropertiesUtils.load(file);
		Set<String> names = properties.stringPropertyNames();
		for (String name : names) {
			config.set(name, properties.getProperty(name));
		}

		return config;
	}

	@Override
	public Config read(InputStream stream) throws IOException {
		Config config = new DownloadConfig();
		Properties properties = PropertiesUtils.load(stream);
		Set<String> names = properties.stringPropertyNames();
		for (String name : names) {
			config.set(name, properties.getProperty(name));
		}

		return config;
	}

	@Override
	public void close() throws IOException {

	}

}
