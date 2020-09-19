package com.badsocket.core;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.badsocket.core.config.Config;
import com.badsocket.core.downloader.factory.ThreadFactory;
import com.badsocket.core.executor.DownloadTaskExecutor;
import com.badsocket.manager.FileManager;
import com.badsocket.manager.Manager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Context for downloader.
 */
public abstract class Context {

	public abstract Manager getThreadManager();

	public abstract FileManager getFileManager();

	public abstract Config getDownloaderConfig();

	public abstract ThreadFactory getThreadFactory();

	public abstract android.content.Context getAndroidContext();

	public abstract NetworkInfo getNetworkInfo();

	public abstract boolean isNetworkAvailable();

	public abstract long getAvailableSpaceSize(File path);

	public abstract NetworkType getNetworkType();

	public abstract ConnectivityManager getConnectivityManager();

	public abstract ExecutorService getThreadExecutor();

	public abstract DownloadTaskExecutor getDownloadTaskExecutor();

}
