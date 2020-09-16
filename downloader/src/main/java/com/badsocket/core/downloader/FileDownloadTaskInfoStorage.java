package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;
import com.badsocket.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java8.util.stream.StreamSupport;

/**
 * Created by skyrim on 2018/1/17.
 */

public class FileDownloadTaskInfoStorage implements DownloadTaskInfoStorage {

	public static final String DEFAULT_TASK_LIST_FILE = "tasks.lst";

	protected File location;


	protected List<DownloadTask> tasks = new ArrayList<>();

	public static class DownloadTaskInfoList implements Serializable {
		List<DownloadTaskInfoListItem> items = new ArrayList<>();
		public final List<DownloadTaskInfoListItem> list() {
			return items;
		}
	}

	public static class DownloadTaskInfoListItem implements Serializable {
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

	public DownloadTask readTask(File file) throws Exception {
		if (file.exists()) {
			return ObjectUtils.readObject(DownloadTask.class, new FileInputStream(file));
		}
		return null;
	}

	@Override
	public void writeList(List<DownloadTask> tasks, File location) throws Exception {
		DownloadTaskInfoList taskInfoList = new DownloadTaskInfoList();
		File taskListFile = new File(location, DEFAULT_TASK_LIST_FILE);
		for (int i = 0; i < tasks.size(); i++) {
			DownloadTask task = tasks.get(i);
			if (task != null) {
				DownloadTaskInfoListItem item = new DownloadTaskInfoListItem(
						i + 1, task.getStorageDir() + DownloaderContext.DS + task.name()
						+ Downloader.DOWNLOAD_TASK_INFO_SUFFIX);

				taskInfoList.list().add(item);
			}
		}

		ObjectUtils.writeObject(taskInfoList, taskListFile);
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
			DownloadTaskInfoList itemsList = ObjectUtils.readObject(DownloadTaskInfoList.class, new FileInputStream(listFile));
			List<DownloadTaskInfoListItem> list = itemsList.list();
			if (itemsList != null && !list.isEmpty()) {
				for (DownloadTaskInfoListItem item : list) {
					DownloadTask task = readTask(new File(item.infoLocation));
					if (task != null) {
						tasks.add(task);
					}
				}
			}
		}

		return tasks;
	}

	@Override
	public void writeTask(DownloadTask task) throws Exception {
		if (task != null && task.isRunning() && !task.isCompleted()) {
			File file = new File(task.getStorageDir(), task.name() + Downloader.DOWNLOAD_TASK_INFO_SUFFIX);
			ObjectUtils.writeObject(task, file);
		}
	}

	@Override
	public void writeAllTasks(List<DownloadTask> tasks) throws Exception {
		for (DownloadTask dt : tasks) {
			writeTask(dt);
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
