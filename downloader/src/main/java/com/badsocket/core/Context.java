package com.badsocket.core;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.badsocket.core.config.Config;
import com.badsocket.core.downloader.factory.ThreadFactory;
import com.badsocket.io.writer.FileWriter;
import com.badsocket.manager.FileManager;
import com.badsocket.manager.Manager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Context for downloader.
 */
public abstract class Context {


	public abstract Manager getThreadManager();


	public abstract FileManager getFileManager();


	public abstract Config getDownloadConfig();


	public abstract ThreadFactory getThreadFactory();


	public abstract FileWriter getFileWriter(String path) throws IOException;


	public abstract FileWriter getFileWriter(File path) throws IOException;


	public abstract FileWriter getFileWriter(String path, long size) throws IOException;


	public abstract FileWriter getFileWriter(File path, long size) throws IOException;


	public abstract android.content.Context getAndroidContext();


	public abstract NetworkInfo getNetworkInfo();


	public abstract boolean isNetworkAvailable();


	public abstract long getAvailableSpaceSize(File path);


	public abstract NetworkType getNetworkType();


	public abstract ConnectivityManager getConnectivityManager();


	public abstract ExecutorService getThreadExecutor();


	public abstract ExecutorService getDownloadTaskExecutor();


	public abstract void finalize();


}
