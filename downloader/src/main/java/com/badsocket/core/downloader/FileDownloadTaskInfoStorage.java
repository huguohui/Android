package com.badsocket.core.downloader;

/**
 * Created by skyrim on 2018/1/17.
 */

public interface FileDownloadTaskInfoStorage extends DownloadTaskInfoStorage {

	public static final String DEFAULT_TASK_LIST_FILE = "tasks.lst";

	Locations location();

	void setLocations(Locations locations);

	final class Locations {
		String taskListLocation;
		String taskInfoDirectory;
		public Locations(String list, String task) {
			taskListLocation = list;
			taskInfoDirectory = task;
		}
	}

}
