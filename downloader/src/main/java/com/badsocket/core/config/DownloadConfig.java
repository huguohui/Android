package com.badsocket.core.config;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by skyrim on 2017/12/16.
 */

public class DownloadConfig implements FileConfig {

	public static final String GLOBAL_MAX_DOWNLOAD_THREADS = "global.max_download_threads";

	public static final String GLOBAL_MAX_PARALLEL_TASKS = "global.max_parallel_tasks";

	public static final String GLOBAL_MAX_CONNECTIONS = "global.max_connections";

	public static final String GLOBAL_DOWNLAOD_PATH = "global.default_download_path";

	public static final String CONFIG_FILE_NAME = "config";

	public static final String CONFIG_FILE_EXT = ".properties";

	public static final String CONFIG_FILE = CONFIG_FILE_NAME + CONFIG_FILE_EXT;

	private Map<String, String> configMap = new ConcurrentHashMap<>();

	private File location;

	public DownloadConfig() {
		this(null);
	}

	public DownloadConfig(File location) {
		this.location = location;
	}

	@Override
	public String get(String key) {
		return configMap.get(key);
	}

	@Override
	public void set(String key, String val) {
		configMap.put(key, val);
	}

	@Override
	public Integer getInteger(String key) {
		Integer integer = null;
		try {
			integer = Integer.parseInt(configMap.get(key));
		}
		catch (Exception e) {
		}

		return integer;
	}

	@Override
	public Float getFloat(String key) {
		Float value = null;
		try {
			value = Float.parseFloat(configMap.get(key));
		}
		catch (Exception e) {
		}

		return value;
	}

	@Override
	public Double getDouble(String key) {
		Double value = null;
		try {
			value = Double.parseDouble(configMap.get(key));
		}
		catch (Exception e) {
		}

		return value;
	}

	@Override
	public Set<String> keySet() {
		return configMap.keySet();
	}

	@Override
	public Collection<String> values() {
		return configMap.values();
	}

	@Override
	public void merge(Config config) {
		for (String key : config.keySet()) {
			set(key, config.get(key));
		}
	}

	@Override
	public File location() {
		return location;
	}
}
