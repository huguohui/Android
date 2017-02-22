package com.downloader.base;

import java.net.URL;

/**
 * Information for download task.
 */
public class DownloadTaskInfo extends TaskInfo {
	/** Start time of task in millisecond. */
	public long startTime;

	/** Finish time of task in millisecond. */
	public long finishTime;

	/** Used time of downloading in millisecond. */
	public long usedTime;

	/** Length of downloading task. */
	public long length;

	/** Url of downloading. */
	public URL url;


	/**
	 * Create a instance of TaskInfo by special name.
	 * @param name Name of downloading task.
	 */
	public DownloadTaskInfo(String name) {
		super(name);
	}
}
