package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.DownloaderContext;
import com.badsocket.core.Task;
import com.badsocket.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skyrim on 2018/1/17.
 */

public class FileDownloadTaskInfoStorage implements DownloadTaskInfoStorage {

	protected File location;

	protected File[] files;

	protected List<DownloadTask> tasks = new ArrayList<>();


	public FileDownloadTaskInfoStorage(File location) {
		this.location = location;
		this.files = FileUtils.listDirectory(location);
	}


	protected DownloadTask read(File file) throws Exception {
		if (file.exists()) {
			return FileUtils.readObject(file);
		}
		return null;
	}


	@Override
	public List<DownloadTask> read() throws Exception {
		if (files.length != 0) {
			for (File file : files) {
				DownloadTask task = read(file);
				if (task != null) {
					tasks.add(task);
				}
			}
		}
		return tasks;
	}


	@Override
	public void write(List<DownloadTask> tasks) throws Exception {
		for (DownloadTask task : tasks) {
			write(task);
		}
	}


	@Override
	public void write(DownloadTask task) throws Exception {
		if (task != null) {
			File file = new File(location, task.getName() + Downloader.DOWNLOAD_TASK_INFO_SUFFIX);
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
