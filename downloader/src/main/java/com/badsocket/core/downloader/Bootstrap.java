package com.badsocket.core.downloader;

import android.os.Environment;

import com.badsocket.core.Context;
import com.badsocket.core.DownloaderContext;
import com.badsocket.core.config.Config;
import com.badsocket.core.config.ConfigReader;
import com.badsocket.core.config.PropertiesConfigReader;

/**
 * Created by skyrim on 2017/12/16.
 */

public class Bootstrap {



	private Context context;

	private ConfigReader configReader;

	private android.content.Context androidContext;

	private Config config;



	public void init() {
		configReader = new PropertiesConfigReader();
		context = new DownloaderContext(androidContext);



	}
















}
