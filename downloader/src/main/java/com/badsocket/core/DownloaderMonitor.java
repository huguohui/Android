package com.badsocket.core;

import com.badsocket.core.downloader.Downloader;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

final public class DownloaderMonitor extends TimerTask implements MonitorDefines {

	List<DownloadTask> downloadTasks;

	Map<DownloadTask, Long> downlaodTaskDetails;

	Downloader downloader;

	long interval = 1000;

	Timer monitorTimer = new Timer();

	DownloaderMonitor(Downloader downloader) {
		this.downloader = downloader;
	}

	public Thread[] allThreads() {
		return null;
	}

	@Override
	public List<DownloadTask> downloadTasks() {
		return null;
	}

	@Override
	public long allDownloadDataCount() {
		return 0;
	}

	@Override
	public Map<DownloadTask, Long> allDownloadDataDetails() {
		return null;
	}

	@Override
	public long globalDownloadSpeed() {
		return 0;
	}

	@Override
	public Map<DownloadTask, Long> allTaskDownloadSpeed() {
		return null;
	}

	@Override
	public long dataWritedTotal() {
		return 0;
	}

	@Override
	public long dataWrited() {
		return 0;
	}

	@Override
	public void interval(long interval) {
		this.interval = interval;
	}

	@Override
	public long interval() {
		return 0;
	}

	@Override
	public void start() {
		monitorTimer.schedule(this, interval);
	}

	@Override
	public void stop() {
		monitorTimer.cancel();
	}

	@Override
	public void run() {
		downloadTasks = downloader.taskList();
		for (DownloadTask task : downloadTasks) {

		}
	}


}


interface MonitorDefines {

	Thread[] allThreads();

	List<DownloadTask> downloadTasks();

	long allDownloadDataCount();

	Map<DownloadTask, Long> allDownloadDataDetails();

	long globalDownloadSpeed();

	Map<DownloadTask, Long> allTaskDownloadSpeed();

	long dataWritedTotal();

	long dataWrited();

	void interval(long interval);

	long interval();

	void start();

	void stop();

}



