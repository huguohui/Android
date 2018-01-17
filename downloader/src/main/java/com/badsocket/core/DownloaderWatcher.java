package com.badsocket.core;

import android.os.Handler;
import android.os.Message;

import com.badsocket.core.downloader.Downloader;
import com.badsocket.util.Log;

import java.util.List;

/**
 * Created by skyrim on 2017/10/15.
 */

public class DownloaderWatcher implements MonitorWatcher {

	public DownloaderWatcher() {
	}


	@Override
	public void watch(Object o) {
		Downloader downloader = (Downloader) o;
		List<DownloadTask> tasks = downloader.taskList();
		for (DownloadTask task : tasks) {
			task.update();
			
		}
	}
}
