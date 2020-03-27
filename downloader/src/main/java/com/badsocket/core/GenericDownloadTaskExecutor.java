package com.badsocket.core;

import com.badsocket.core.downloader.DownloaderContext;

import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Created by skyrim on 2017/12/15.
 */
public class GenericDownloadTaskExecutor extends com.badsocket.core.AbstractDownloadTaskExecutor {

	public GenericDownloadTaskExecutor(int corePoolSize) {
		super(corePoolSize);
	}

	public GenericDownloadTaskExecutor(int coolPoolSize, ThreadFactory factory) {
		super(coolPoolSize, factory);
	}

	public GenericDownloadTaskExecutor(DownloaderContext context, int corePoolSize) {
		super(context, corePoolSize);
	}
}
