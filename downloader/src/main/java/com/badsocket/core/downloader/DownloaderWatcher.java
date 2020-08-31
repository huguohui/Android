package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;

import java.util.List;
import java8.util.stream.StreamSupport;

/**
 * Created by skyrim on 2017/10/15.
 */

public class DownloaderWatcher {

	protected Downloader downloader;

	protected List<DownloadTask> tasks;

	protected DownloadTaskInfoStorage taskInfoStorage;

	public DownloaderWatcher(Downloader downloader) {
		this.downloader = downloader;
		taskInfoStorage = downloader.getDownloadTaskStorage();
	}

	protected void updateTask(DownloadTask task) {
		if (task != null && !task.isCompleted()) {
			task.update();
		}
	}

	protected void recordTasks(List<DownloadTask> tasks) throws Exception {
		taskInfoStorage.writeList(tasks);
	}

	protected void handleTask() {
		try {
			StreamSupport.stream(tasks).forEach((t) -> updateTask(t));
			recordTasks(tasks);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void watch(Object o) {
		tasks = downloader.taskList();

		handleTask();
	}
}
