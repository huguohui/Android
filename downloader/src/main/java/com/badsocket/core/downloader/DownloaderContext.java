package com.badsocket.core.downloader;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.badsocket.core.GenericDownloadTaskExecutor;
import com.badsocket.core.NetworkType;
import com.badsocket.core.config.Config;
import com.badsocket.core.config.DownloadConfig;
import com.badsocket.core.config.PropertiesConfigReader;
import com.badsocket.core.downloader.factory.ThreadFactory;
import com.badsocket.core.executor.DownloadTaskExecutor;
import com.badsocket.io.ConcurrentFileAccessor;
import com.badsocket.util.FileManager;
import com.badsocket.core.ThreadManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static com.badsocket.core.config.ConfigKeys.GLOBAL_MAX_PARALLEL_TASKS;
import static com.badsocket.core.config.ConfigKeys.THREAD_POOL_SIZE;

/**
 * Created by skyrim on 2017/12/15.
 */

public class DownloaderContext  {

	public static final String EXTERNAL_STORAGE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

	public static final String DS = "/";

	public static final String CONFIG_DIR = "configs";

	public static final String ICON_DIR = "icons";

	public static final String PLUGIN_DIR = "plugins";

	public static final String HISTORY_DIR = "history";

	public static final String PATCHES_DIR = "patches";

	public static String HOME_DIRECTORY;

	private android.content.Context androidContext;

	private ThreadManager threadManager;

	private ThreadFactory threadFactory;

	private FileManager fileManger;

	private Config config;

	private NetworkInfo networkInfo;

	private boolean isNetworkAvailable;

	private StatFs pathStat;

	private NetworkType networkType;

	private ConnectivityManager connectivityManager;

	private ScheduledExecutorService threadExecutor;

	private DownloadTaskExecutor downloadTaskExecutor;

	private Map<String, ConcurrentFileAccessor> fileWriters = new HashMap<>();

	public DownloaderContext(android.content.Context androidContext) {
		this.androidContext = androidContext;
		HOME_DIRECTORY = EXTERNAL_STORAGE_ROOT + DS + androidContext.getApplicationInfo().packageName;

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
		threadFactory = threadManager;
		connectivityManager = (ConnectivityManager) androidContext.getSystemService(
				android.content.Context.CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getActiveNetworkInfo();
		config = new PropertiesConfigReader(new File(getExternalFile(CONFIG_DIR), DownloadConfig.CONFIG_FILE)).read();
		threadExecutor = new ScheduledThreadPoolExecutor(config.getInteger(THREAD_POOL_SIZE), threadFactory);
		downloadTaskExecutor = new GenericDownloadTaskExecutor(this, config.getInteger(GLOBAL_MAX_PARALLEL_TASKS), threadFactory);
	}

	public static File getExternalFile(String file) {
		return new File(HOME_DIRECTORY, file);
	}

	protected void checkEnvironment() {
		isNetworkAvailable = networkInfo.isConnected() && networkInfo.isAvailable();
	}

	public ThreadManager getThreadManager() {
		return threadManager;
	}

	public FileManager getFileManager() {
		return fileManger;
	}

	public Config getDownloaderConfig() {
		return config;
	}

	public ThreadFactory getThreadFactory() {
		return threadFactory;
	}

	public android.content.Context getAndroidContext() {
		return androidContext;
	}

	public NetworkInfo getNetworkInfo() {
		return networkInfo;
	}

	public boolean isNetworkAvailable() {
		return isNetworkAvailable;
	}

	public long getAvailableSpaceSize(String path) {
		pathStat = new StatFs(path);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			return pathStat.getAvailableBytes();
		}
		else {
			return 0;
		}
	}

	public NetworkType getNetworkType() {
		return networkType;
	}

	public ConnectivityManager getConnectivityManager() {
		return connectivityManager;
	}

	public ExecutorService getThreadExecutor() {
		return threadExecutor;
	}

	public DownloadTaskExecutor getDownloadTaskExecutor() {
		return downloadTaskExecutor;
	}
}
