package com.badsocket.core;

import android.app.Application;
import android.content.res.AssetManager;

import com.badsocket.core.Context;
import com.badsocket.core.DownloaderContext;
import com.badsocket.core.config.Config;
import com.badsocket.core.config.ConfigReader;
import com.badsocket.manager.FileManager;
import com.badsocket.util.FileUtils;
import com.badsocket.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by skyrim on 2017/12/16.
 */

public class Bootstrap extends Application {

	private Context context;

	private ConfigReader configReader;

	private android.content.Context androidContext;

	private Config config;

	private FileManager fileManager;

	private AssetManager assetManager;

	private String packageName;


	public void onCreate() {
		androidContext = this;
		initAll();

	}


	public void initAll() {
		initEnvironment();
		initContext();
	}


	public void initEnvironment() {
		packageName = androidContext.getApplicationInfo().packageName;
		assetManager = androidContext.getAssets();
		extractAssets();
	}


	public void extractAssets() {
		File rootPath = new File(DownloaderContext.ROOT_PATH, packageName);

		if (!rootPath.isDirectory()) {
			Log.error("", "The path: " + rootPath.getAbsolutePath() + " isn't directory!");
			return;
		}
		if (!rootPath.canRead() || !rootPath.canWrite()) {
			Log.error("", "The path: " + rootPath.getAbsolutePath() + " can't read or write!");
			return;
		}

		try {
			for (String file : assetManager.list(".")) {
				FileUtils.copyTo(assetManager.open(file), rootPath);
			}
		}
		catch (IOException e) {
			Log.e(e);
		}
		finally {
			assetManager.close();
		}
	}


	public void initContext() {
		context = new DownloaderContext(androidContext);
	}








}
