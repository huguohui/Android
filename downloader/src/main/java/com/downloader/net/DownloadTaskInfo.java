package com.downloader.net;

import java.net.URL;

/**
 * Information for download task.
 */
public class DownloadTaskInfo extends AbstractTaskInfo {
	/** Start time of task in millisecond. */
	protected long startTime;

	/** Finish time of task in millisecond. */
	protected long finishTime;

	/** Used time of downloading in millisecond. */
	protected long usedTime;

	/** Length of downloading task. */
	protected long length;

	/** Url of downloading. */
	protected URL url;


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
