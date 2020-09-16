package com.badsocket.core.downloader;

import com.badsocket.core.DownloadTask;

import java.io.File;
import java.util.List;

/**
 * Created by skyrim on 2018/1/17.
 */

public interface DownloadTaskInfoStorage {

	List<DownloadTask> readList() throws Exception;

	List<DownloadTask> readList(File location) throws Exception;

	DownloadTask readTask(File infoFile) throws Exception;

	void writeList(List<DownloadTask> tasks) throws Exception;

	void writeList(List<DownloadTask> tasks, File location) throws Exception;

	void writeTask(DownloadTask task) throws Exception;

	void writeAllTasks(List<DownloadTask> tasks) throws Exception;

	File location();

	void setLocation(File location);

}
