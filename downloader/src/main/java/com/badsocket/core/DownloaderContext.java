package com.badsocket.core;

import android.content.pm.PermissionInfo;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;

import com.badsocket.core.config.Config;
import com.badsocket.core.config.DownloadConfig;
import com.badsocket.core.config.PropertiesConfigReader;
import com.badsocket.io.writer.ConcurrentFileWriter;
import com.badsocket.io.writer.FileWriter;
import com.badsocket.manager.FileManager;
import com.badsocket.manager.Manager;
import com.badsocket.manager.SimpleFileManager;
import com.badsocket.manager.ThreadManager;
import com.badsocket.manager.factory.BaseThreadFactory;
import com.badsocket.manager.factory.ThreadFactory;
import com.badsocket.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by skyrim on 2017/12/15.
 */

public class DownloaderContext extends Context {

	public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

	public static final String DS = "/";

	public static final String CONFIG_DIR = "config";

	public static final String ICON_DIR = "icon";

	public static String HOME_DIRECTORY;

	private android.content.Context androidContext;

	private Manager threadManager;

	private ThreadFactory threadFactory;

	private FileManager fileManger;

	private Config config;

	private NetworkInfo networkInfo;

	private boolean isNetworkAvailable;

	private StatFs pathStat;

	private NetworkType networkType;

	public DownloaderContext(android.content.Context androidContext) {
		this.androidContext = androidContext;
		HOME_DIRECTORY = ROOT_PATH + DS + androidContext.getApplicationInfo().packageName;
		init();
	}


	public DownloaderContext() {
		this(null);
	}


	protected void init() {
		threadManager = ThreadManager.getInstance();
		threadFactory = new BaseThreadFactory();
		networkInfo = (NetworkInfo) androidContext.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);

		try {
			fileManger = SimpleFileManager.getInstance();
			fileManger.createDirectory(new File(HOME_DIRECTORY));
			config = new PropertiesConfigReader(
					new File(ROOT_PATH, DownloadConfig.CONFIG_FILE)).read();
		}
		catch(Exception e) {
			Log.e(e);
		}
	}


	protected void checkEnvironment() {
		isNetworkAvailable = networkInfo.isConnected() && networkInfo.isAvailable();
		pathStat = new StatFs(HOME_DIRECTORY);

	}


	@Override
	public Manager getThreadManager() {
		return threadManager;
	}


	@Override
	public FileManager getFileManager() {
		return fileManger;
	}


	@Override
	public Config getDownloadConfig() {
		return config;
	}


	@Override
	public ThreadFactory getThreadFactory() {
		return threadFactory;
	}


	@Override
	public FileWriter getFileWriter(String path) throws IOException {
		return getFileWriter(new File(path));
	}


	@Override
	public FileWriter getFileWriter(File path) throws IOException {
		return new ConcurrentFileWriter(path);
	}


	@Override
	public FileWriter getFileWriter(String path, long size) throws IOException {
		return getFileWriter(new File(path), size);
	}


	@Override
	public FileWriter getFileWriter(File path, long size) throws IOException {
		return new ConcurrentFileWriter(path, size);
	}


	@Override
	public android.content.Context getAndroidContext() {
		return null;
	}


	@Override
	public NetworkInfo getNetworkInfo() {
		return null;
	}


	@Override
	public boolean isNetworkAvailable() {
		return isNetworkAvailable;
	}


	@Override
	public long getAvailableSpaceSize(File path) {
		return pathStat.getFreeBytes();
	}


	@Override
	public NetworkType getNetworkType() {
		return null;
	}
}
