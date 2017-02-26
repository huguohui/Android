package com.downloader.base;

import java.net.URL;

/**
 * Information for download task.
 */
public class DownloadTaskInfo extends AbstractTaskInfo {
	/** Start time of task in millisecond. */
	private long startTime;

	/** Finish time of task in millisecond. */
	private long finishTime;

	/** Used time of downloading in millisecond. */
	private long usedTime;

	/** Length of downloading task. */
	private long length;

	/** Url of downloading. */
	private URL url;


	/**
	 * Create a instance of AbstractTaskInfo by special name.
	 * @param name Name of downloading task.
	 */
	public DownloadTaskInfo(String name) {
		super(name);
	}


	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long mStartTime) {
		startTime = mStartTime;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long mFinishTime) {
		finishTime = mFinishTime;
	}

	public long getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(long mUsedTime) {
		usedTime = mUsedTime;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long mLength) {
		length = mLength;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL mUrl) {
		url = mUrl;
	}
}
