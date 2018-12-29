package com.badsocket.core.downloader;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.badsocket.core.Context;
import com.badsocket.core.executor.GenericDownloadTaskExecutor;
import com.badsocket.core.NetworkType;
import com.badsocket.core.ThreadExecutor;
import com.badsocket.core.config.Config;
import com.badsocket.core.config.DownloadConfig;
import com.badsocket.core.config.PropertiesConfigReader;
import com.badsocket.core.downloader.factory.BaseThreadFactory;
import com.badsocket.core.downloader.factory.ThreadFactory;
import com.badsocket.io.writer.ConcurrentFileWriter;
import com.badsocket.io.writer.FileWriter;
import com.badsocket.manager.FileManager;
import com.badsocket.manager.Manager;
import com.badsocket.manager.ThreadManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by skyrim on 2017/12/15.
 */

public class DownloaderContext extends Context {

	public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

	public static final String DS = "/";

	public static final String CONFIG_DIR = "configs";

	public static final String ICON_DIR = "icons";

	public static final String PLUGIN_DIR = "plugins";

	public static final String HISTORY_DIR = "history";

	public static final String PATCHES_DIR = "patches";

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

	private ConnectivityManager connectivityManager;

	private ExecutorService threadExecutor;

	private ExecutorService downloadTaskExecutor;

	private Map<String, ConcurrentFileWriter> fileWriters = new HashMap<>();


	public DownloaderContext(android.content.Context androidContext) {
		this.androidContext = androidContext;
		HOME_DIRECTORY = ROOT_PATH + DS + androidContext.getApplicationInfo().packageName;

		try {
			init();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	public DownloaderContext() {
		this(null);
	}


	protected void init() throws IOException {
		threadManager = ThreadManager.getInstance();
		threadFactory = new BaseThreadFactory();
		connectivityManager = (ConnectivityManager) androidContext.getSystemService(
				android.content.Context.CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getActiveNetworkInfo();
		config = new PropertiesConfigReader(
				new File(HOME_DIRECTORY + DS + CONFIG_DIR, DownloadConfig.CONFIG_FILE)).read();
		threadExecutor = new ThreadExecutor(
				config.getInteger(DownloadConfig.GLOBAL_MAX_DOWNLOAD_THREADS)
						* config.getInteger(DownloadConfig.GLOBAL_MAX_PARALLEL_TASKS), threadFactory);
		downloadTaskExecutor = new GenericDownloadTaskExecutor(
				config.getInteger(DownloadConfig.GLOBAL_MAX_PARALLEL_TASKS), threadFactory);
	}


	protected void checkEnvironment() {
		isNetworkAvailable = networkInfo.isConnected() && networkInfo.isAvailable();
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
		ConcurrentFileWriter fileWriter =  new ConcurrentFileWriter(new File(
				path.getPath() + Downloader.UNCOMPLETE_DOWNLAOD_TASK_SUFFIX), size);
		synchronized (fileWriters) {
			fileWriters.put(path.getAbsolutePath(), fileWriter);
		}
		return fileWriter;
	}


	@Override
	public android.content.Context getAndroidContext() {
		return androidContext;
	}


	@Override
	public NetworkInfo getNetworkInfo() {
		return networkInfo;
	}


	@Override
	public boolean isNetworkAvailable() {
		return isNetworkAvailable;
	}


	@Override
	public long getAvailableSpaceSize(File path) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			return pathStat.getFreeBytes();
		}
		else {
			return 0;
		}
	}


	@Override
	public NetworkType getNetworkType() {
		return networkType;
	}


	@Override
	public ConnectivityManager getConnectivityManager() {
		return connectivityManager;
	}


	@Override
	public ExecutorService getThreadExecutor() {
		return threadExecutor;
	}


	@Override
	public ExecutorService getDownloadTaskExecutor() {
		return downloadTaskExecutor;
	}


	@Override
	public void finalize() {
		
	}


}
