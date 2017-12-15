package com.badsocket.core;

import com.badsocket.io.writer.ConcurrentFileWriter;
import com.badsocket.io.writer.FileWriter;
import com.badsocket.manager.FileManager;
import com.badsocket.manager.Manager;
import com.badsocket.manager.ThreadManager;
import com.badsocket.manager.factory.BaseThreadFactory;
import com.badsocket.manager.factory.ThreadFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by skyrim on 2017/12/15.
 */

public class DownloaderContext extends Context {

	private android.content.Context androidContext;

	private Manager threadManager;

	private ThreadFactory threadFactory;

	private Manager fileManger;

	private Config config;


	public DownloaderContext(android.content.Context androidContext) {
		this.androidContext = androidContext;

		try {
			init();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}


	public DownloaderContext() {
		this(null);
	}


	protected void init() throws Exception {
		threadManager = ThreadManager.getInstance();
		threadFactory = new BaseThreadFactory();
		fileManger = new FileManager();

	}


	@Override
	public Manager getThreadManager() {
		return threadManager;
	}


	@Override
	public Manager getFileManager() {
		return fileManger;
	}


	@Override
	public Config getConfig() {
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
}
