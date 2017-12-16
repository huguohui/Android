package com.badsocket.core.config;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by skyrim on 2017/12/16.
 */

public class DownloadConfig implements Config {

	public static final String GLOBAL_MAX_DOWNLOAD_THREADS = "gloabl.max_download_threads";

	public static final String GLOBAL_MAX_PARALLEL_TASKS = "gloabl.max_parallel_tasks";

	public static final String GLOBAL_MAX_CONNECTIONS = "gloabl.max_connections";

	public static final String GLOBAL_DOWNLAOD_PATH = "gloabl.max_download_path";

	public static final String CONFIG_FILE_NAME = "config";

	public static final String CONFIG_FILE_EXT = ".properties";

	public static final String CONFIG_FILE = CONFIG_FILE_NAME + CONFIG_FILE_EXT;


	private Map<String, Object> configMap = new ConcurrentHashMap<>();

	private File location;


	public DownloadConfig() {
		this(null);
	}


	public DownloadConfig(File location) {
		this.location = location;
	}


	@Override
	public <T> T get(String key) {
		return (T) configMap.get(key);
	}


	@Override
	public <T> void set(String key, T val) {
		configMap.put(key, val);
	}


	@Override
	public Set<String> keySet() {
		return configMap.keySet();
	}


	@Override
	public Collection<Object> values() {
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
