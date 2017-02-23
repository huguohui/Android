package com.downloader.manager;

import java.net.URL;

/**
 * Descriptor for downloading task.
 */
public class DownloadTaskDescriptor extends AbstractDescriptor {
	/** URL for downloading. */
	private URL mUrl;

	/** Task priority. */
	private int mPriority;

	/** Number of max used for threads. */
	private int mMaxThread;

	/** Task start time. */
	private int mStartTime;


	public URL getUrl() {
		return mUrl;
	}

	public void setUrl(URL mUrl) {
		this.mUrl = mUrl;
	}

	public int getPriority() {
		return mPriority;
	}

	public void setPriority(int mPriority) {
		this.mPriority = mPriority;
	}

	public int getMaxThread() {
		return mMaxThread;
	}

	public void setMaxThread(int mMaxThread) {
		this.mMaxThread = mMaxThread;
	}

	public int getStartTime() {
		return mStartTime;
	}

	public void setStartTime(int mStartTime) {
		this.mStartTime = mStartTime;
	}
}
