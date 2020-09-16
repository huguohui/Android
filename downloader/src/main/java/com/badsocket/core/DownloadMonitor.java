package com.badsocket.core;

import com.badsocket.core.downloader.ControlableClock;
import com.badsocket.core.downloader.Downloader;

import java.util.List;
import java.util.Map;
import java.util.Timer;

import java8.util.stream.StreamSupport;

final public class DownloadMonitor
		implements MonitorDefines, SpeedCounter, ControlableClock.OnTickListener
{
	List<DownloadTask> downloadTasks;

	Map<DownloadTask, TaskDownloadDataDetails> downloadTaskDetails;

	Downloader downloader;

	long interval = 1000;

	long transferedDataSize = 0;

	long lastTransferedDataSize = 0;

	long writedDataSize = 0;

	long lastWritedDataSize = 0;

	Timer monitorTimer = new Timer();

	SimpleControlableClock simpleControlableClock = new SimpleControlableClock();

	static class TaskDownloadDataDetails {
		long currentReceivedSize;
		long downloadedSize;

		TaskDownloadDataDetails(long a, long b) {
			currentReceivedSize = a;
			downloadedSize = b;
		}
	}

	DownloadMonitor(Downloader downloader) {
		this.downloader = downloader;
		simpleControlableClock.addOnTickListener(this);
	}

	public List<Thread> allThreads() {
		return downloader.getDownloaderContext().getThreadManager().list();
	}

	@Override
	public List<DownloadTask> downloadTasks() {
		return null;
	}

	@Override
	public long allDownloadDataCount() {
		return transferedDataSize;
	}

	@Override
	public Map<DownloadTask, TaskDownloadDataDetails> allDownloadDataDetails() {
		return downloadTaskDetails;
	}

	@Override
	public long globalDownloadSpeed() {
		return lastWritedDataSize;
	}

	@Override
	public long dataWritedTotal() {
		return writedDataSize;
	}

	@Override
	public long currentDataWrited() {
		long size = writedDataSize - lastWritedDataSize;
		lastWritedDataSize = writedDataSize;
		return size;
	}

	@Override
	public void onTick() {
		doMonitor();
	}

	public void doMonitor() {
		downloadTasks = downloader.taskList();
		for (DownloadTask task : downloadTasks) {
			TaskDownloadDataDetails lastDetails = new TaskDownloadDataDetails(0, 0);
			if (downloadTaskDetails.containsKey(task)) {
				lastDetails = downloadTaskDetails.get(task);
			}

			lastDetails.currentReceivedSize = task.downloadedSize() - lastDetails.downloadedSize;
			lastDetails.downloadedSize = task.downloadedSize();
			downloadTaskDetails.put(task, lastDetails);
		}

		StreamSupport.stream(downloadTaskDetails.entrySet()).forEach(v -> {
			lastTransferedDataSize += v.getValue().currentReceivedSize;
			transferedDataSize += v.getValue().downloadedSize;
		});
	}

	@Override
	public void count(long size) {
		writedDataSize += size;
	}

	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {
		simpleControlableClock.start();
	}

	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {
		simpleControlableClock.pause();
	}

	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume() throws Exception {
		simpleControlableClock.resume();
	}

	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() throws Exception {
		simpleControlableClock.stop();
	}
}

interface MonitorDefines extends Controlable {

	List<Thread> allThreads();

	List<DownloadTask> downloadTasks();

	long allDownloadDataCount();

	Map<DownloadTask, DownloadMonitor.TaskDownloadDataDetails> allDownloadDataDetails();

	long globalDownloadSpeed();

	long dataWritedTotal();

	long currentDataWrited();

}



