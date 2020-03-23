package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.util.CollectionUtils;

import java.util.List;

/**
 * Created by skyrim on 2017/10/15.
 */

public class DownloaderWatcher implements MonitorWatcher {

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
			CollectionUtils.forEach(tasks, (CollectionUtils.Action<DownloadTask>) (task) -> {
				updateTask(task);
			});
			recordTasks(tasks);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void watch(Object o) {
		tasks = downloader.taskList();

		handleTask();
	}
}
