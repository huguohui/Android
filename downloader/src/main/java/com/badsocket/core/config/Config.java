package com.badsocket.core.config;

import java.io.File;
import java.util.Collection;
import java.util.Set;

/**
 * Created by skyrim on 2017/12/15.
 */
public interface Config {

	String get(String key);

	void set(String key, String val);

	Integer getInteger(String key);

	Float getFloat(String key);

	Double getDouble(String key);

	Set<String> keySet();

	Collection<String> values();

	void merge(Config config);

	File location();

}
