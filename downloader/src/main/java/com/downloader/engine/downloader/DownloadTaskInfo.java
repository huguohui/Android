package com.downloader.engine.downloader;

import com.downloader.engine.AbstractTaskInfo;

import java.net.URL;

/**
 * Information for download task.
 */
public class DownloadTaskInfo extends AbstractTaskInfo {
	protected final static byte[] FILE_FORMAT_CHARS = {'D', 'T', 'I'};

	public final static int MAX_NAME_LEN = 0xff;

	/** Url of downloading. */
	protected URL url;

	/** Start time of task in millisecond. */
	protected long startTime;

	/** Finish time of task in millisecond. */
	protected long finishTime;

	/** Used time of downloading in millisecond. */
	protected long usedTime;

	protected int priority;

	/** Length of downloading task. */
	protected long length;

	protected long downloadLength;

	protected int totalThreads;

	protected long[] partOffsetStart;

	protected long[] partLength;

	protected long[] partDownloadLength;


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


	public long getDownloadLength() {
		return downloadLength;
	}


	public DownloadTaskInfo setDownloadLength(long downloadLength) {
		this.downloadLength = downloadLength;
		return this;
	}


	public int getTotalThreads() {
		return totalThreads;
	}


	public DownloadTaskInfo setTotalThreads(int totalThreads) {
		this.totalThreads = totalThreads;
		return this;
	}


	public long[] getPartOffsetStart() {
		return partOffsetStart;
	}


	public DownloadTaskInfo setPartOffsetStart(long[] partOffsetStart) {
		this.partOffsetStart = partOffsetStart;
		return this;
	}


	public long[] getPartLength() {
		return partLength;
	}


	public DownloadTaskInfo setPartLength(long[] partLength) {
		this.partLength = partLength;
		return this;
	}


	public long[] getPartDownloadLength() {
		return partDownloadLength;
	}


	public DownloadTaskInfo setPartDownloadLength(long[] partDownloadLength) {
		this.partDownloadLength = partDownloadLength;
		return this;
	}


	public int getPriority() {
		return priority;
	}


	public DownloadTaskInfo setPriority(int priority) {
		this.priority = priority;
		return this;
	}
}
