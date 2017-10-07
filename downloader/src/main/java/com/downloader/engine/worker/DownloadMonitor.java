package com.downloader.engine.worker;

import com.downloader.engine.AbstractMonitor;
import com.downloader.engine.downloader.Downloader;

/**
 * Created by skyrim on 2017/10/7.
 */

public class DownloadMonitor extends AbstractMonitor {

	protected Downloader monitoredDownloader;


	public DownloadMonitor(int interval) {
		super(interval);
	}


	protected void collectData(Downloader d) {

	}


	protected void doMonitor() {
		if (monitored == null) {
			return;
		}
		monitoredDownloader = (Downloader) monitored;

	}


	@Override
	public Object collectedData() {
		return null;
	}
}
