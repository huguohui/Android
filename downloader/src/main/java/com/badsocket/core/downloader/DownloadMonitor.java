package com.badsocket.core.downloader;


import com.badsocket.core.AbstractMonitor;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.downloader.factory.ThreadFactory;

import java.util.concurrent.ExecutorService;

/**
 * Created by skyrim on 2017/10/9.
 */
public class DownloadMonitor extends AbstractMonitor {

	protected Downloader downloader;

	protected ExecutorService executeServcie;


	public DownloadMonitor(Downloader downloader, int interval) {
		super(interval);
		this.downloader = downloader;
	}


	public void run() {
		super.run();
	}


	@Override
	public boolean isStoped() {
		return false;
	}
}
