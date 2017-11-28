package com.badsocket.core.downloader;


import com.badsocket.core.AbstractMonitor;

/**
 * Created by skyrim on 2017/10/9.
 */
public class DownloadMonitor extends AbstractMonitor {


	public DownloadMonitor(int interval) {
		super(interval);
	}


	public void run() {
		super.run();
	}


	@Override
	public boolean isStoped() {
		return false;
	}
}
