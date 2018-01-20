package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;
import com.badsocket.util.FileUtils;
import com.badsocket.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skyrim on 2018/1/17.
 */

public class FileDownloadTaskInfoStorage implements DownloadTaskInfoStorage {

	public static final String DEFAULT_TASK_LIST_FILE = "tasks.lst";

	protected File location;

	protected List<DownloadTask> tasks = new ArrayList<>();

	protected static class DownloadTaskInfoListItem implements Serializable {
		int taskIndex;
		String infoLocation;

		public DownloadTaskInfoListItem(int index, String location) {
			taskIndex = index;
			infoLocation = location;
		}
	}


	public FileDownloadTaskInfoStorage(File location) {
		this.location = location;
	}


	public DownloadTask read(File file) throws Exception {
		if (file.exists()) {
			return FileUtils.readObject(file);
		}
		return null;
	}


	@Override
	public void writeList(List<DownloadTask> tasks, File location) throws Exception {
		List<DownloadTaskInfoListItem> taskInfoItems = new ArrayList<>();
		File taskListFile = new File(location, DEFAULT_TASK_LIST_FILE);
		for (int i = 0; i < tasks.size(); i++) {
			DownloadTask task = tasks.get(i);
			if (task != null) {
				taskInfoItems.add(new DownloadTaskInfoListItem(
						i + 1, task.getDownloadPath() + DownloaderContext.DS + task.getName()
								+ Downloader.DOWNLOAD_TASK_INFO_SUFFIX));
				write(task);
			}
		}

		FileUtils.writeObject(taskInfoItems, taskListFile);
	}


	@Override
	public void writeList(List<DownloadTask> tasks) throws Exception {
		writeList(tasks, location);
	}


	@Override
	public List<DownloadTask> readList() throws Exception {
		return readList(location);
	}


	@Override
	public List<DownloadTask> readList(File location) throws Exception {
		File listFile = new File(location, DEFAULT_TASK_LIST_FILE);
		if (listFile.exists()) {
			List<DownloadTaskInfoListItem> items = FileUtils.readObject(listFile);
			if (items != null && items.size() != 0) {
				for (DownloadTaskInfoListItem item : items) {
					DownloadTask task = read(new File(item.infoLocation));
					if (task != null) {
						tasks.add(task);
					}
				}
			}
		}

		return tasks;
	}


	@Override
	public void write(DownloadTask task) throws Exception {
		if (task != null && task.isRunning()) {
			File file = new File(task.getDownloadPath(), task.getName() + Downloader.DOWNLOAD_TASK_INFO_SUFFIX);
			FileUtils.writeObject(task, file);
		}
	}


	@Override
	public File location() {
		return location;
	}


	@Override
	public void setLocation(File location) {
		this.location = location;
	}
}
