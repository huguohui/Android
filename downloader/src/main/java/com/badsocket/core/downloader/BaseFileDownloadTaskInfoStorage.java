package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.Task;
import com.badsocket.util.FileChannelHelper;
import com.badsocket.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skyrim on 2018/1/17.
 */

public class BaseFileDownloadTaskInfoStorage implements FileDownloadTaskInfoStorage {

	protected Locations locations;

	protected List<DownloadTask> tasks = new ArrayList<>();

	protected TaskList taskList;

	private List<FileOutputStream> streams = new ArrayList<>();

	private FileOutputStream listOutputStream;

	public BaseFileDownloadTaskInfoStorage(Locations locations) {
		this.locations = locations;
	}

	@Override
	public void open() throws Exception {
		listOutputStream = new FileOutputStream(locations.taskListLocation);
	}

	@Override
	public void close() throws Exception {
		listOutputStream.flush();
		listOutputStream.close();
	}

	protected DownloadTask readTask(File file) throws Exception {
		return ObjectUtils.readObject(DownloadTask.class, new FileInputStream(file), true);
	}

	@Override
	public TaskList read() throws Exception {
		taskList = ObjectUtils.readObject(TaskList.class, new FileInputStream(locations.taskListLocation), true);
		return taskList;
	}

	@Override
	public DownloadTask read(int idx) throws Exception {
		TaskListItem theItem = null;
		for (TaskListItem item : taskList.list()) {
			if (item.taskIndex == idx) {
				theItem = item;
			}
		}
		if (theItem == null) {
			throw new TaskNotFoundException();
		}

		return readTask(new File(new File(locations.taskInfoDirectory, theItem.taskName), DEFAULT_TASK_LIST_FILE));
	}

	protected void writeTask(DownloadTask task) throws Exception {
		if (task != null && task.isRunning() && !task.isCompleted()) {
			File file = new File(task.getStorageDir(), task.name() + Downloader.DOWNLOAD_TASK_INFO_SUFFIX);
			ObjectUtils.writeObject(task, listOutputStream, false);
		}
	}

	protected TaskList toTaskList(List<DownloadTask> tasks) {
		TaskList list = new TaskList();
		for (int i = 0; i < tasks.size(); i++) {
			list.list().add(new TaskListItem(i, tasks.get(i).name()));
		}
		return list;
	}

	public void write(List<DownloadTask> tasks) throws Exception {
		ObjectUtils.writeObject(toTaskList(tasks), listOutputStream, false);
	}

	@Override
	public void update(DownloadTask task) throws Exception {

	}

	@Override
	public void delete(DownloadTask task) throws Exception {

	}

	@Override
	public Locations location() {
		return locations;
	}

	@Override
	public void setLocations(Locations locations) {
		this.locations = locations;
	}
}
