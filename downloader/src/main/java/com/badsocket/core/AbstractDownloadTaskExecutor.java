package com.badsocket.core;


import com.badsocket.core.downloader.DownloaderContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * Created by skyrim on 2017/12/15.
 */
public abstract class AbstractDownloadTaskExecutor implements DownloadTaskExecutor {

	private Map<Task, Future<Task>> futureMap = new ConcurrentHashMap<>();

	private DownloaderContext context;

	public AbstractDownloadTaskExecutor(DownloaderContext context) {

	}


}
