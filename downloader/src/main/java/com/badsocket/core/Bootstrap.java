package com.badsocket.core;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.Environment;

import com.badsocket.core.config.Config;
import com.badsocket.core.config.ConfigReader;
import com.badsocket.core.downloader.DownloaderContext;
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

	public static final String DS = "/";

	public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

	public static final String[] ASSETS_DIRS = {
			"configs",
			"plugins",
			"icons",
			"history",
			"patches"
	};


	public void onCreate() {
		androidContext = this;
		initAll();

	}


	public void initAll() {
		try {
			initEnvironment();
			initContext();
		}
		catch (Exception e) {
			Log.e(e);
		}
	}


	public void initEnvironment() throws IOException {
		packageName = androidContext.getApplicationInfo().packageName;
		assetManager = androidContext.getAssets();
		extractAssets();
	}


	public void extractAssets() {
		File rootPath = new File(DownloaderContext.ROOT_PATH, packageName);
		if (!rootPath.exists()) {
			rootPath.mkdirs();
		}

		if (!rootPath.isDirectory()) {
			Log.d("The path: " + rootPath.getAbsolutePath() + " isn't directory!");
			return;
		}
		if (!rootPath.canRead() || !rootPath.canWrite()) {
			Log.d("The path: " + rootPath.getAbsolutePath() + " can't readTask or writeTask!");
			return;
		}

		for (String assetsDir : ASSETS_DIRS) {
			try {
				File extractDir = new File(rootPath + DS + assetsDir);
				if (!extractDir.exists()) {
					extractDir.mkdirs();
				}
				for (String file : assetManager.list(assetsDir)) {
					FileUtils.copyTo(assetManager.open(assetsDir + DS + file), new File(extractDir, file));
				}
			} catch (Exception e) {
				Log.e(e);
			}
		}
	}


	public void initContext() {
		context = new DownloaderContext(androidContext);
	}


}
