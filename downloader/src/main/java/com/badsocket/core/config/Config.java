package com.badsocket.core.config;

import java.io.File;
import java.util.Collection;
import java.util.Set;

/**
 * Created by skyrim on 2017/12/15.
 */
public interface Config {


	<T> T get(String key);


	<T> void set(String key, T val);


	Set<String> keySet();


	Collection<Object> values();


	void merge(Config config);


	File location();


}
