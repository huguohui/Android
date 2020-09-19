package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skyrim on 2018/1/17.
 */

public interface DownloadTaskInfoStorage {

	void open() throws Exception;

	void close() throws Exception;

	TaskList read() throws Exception;

	DownloadTask read(int idx) throws Exception;

	void write(List<DownloadTask> tasks) throws Exception;

	void update(DownloadTask task) throws Exception;

	void delete(DownloadTask task) throws Exception;

	final class TaskList implements Serializable {
		List<TaskListItem> items = new ArrayList<>();
		public final List<TaskListItem> list() {
			return items;
		}
	}

	final class TaskListItem implements Serializable {
		int taskIndex;
		String taskName;
		public TaskListItem(int idx, String name) {
			taskIndex = idx;
			taskName = name;
		}
	}
}
