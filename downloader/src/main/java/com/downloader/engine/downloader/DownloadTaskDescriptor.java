package com.downloader.engine.downloader;

import java.io.File;
import java.net.URL;

/**
 * Descriptor for downloading task.
 */
public class DownloadTaskDescriptor extends AbstractDescriptor {
	/** URL for downloading. */
	protected URL mUrl;

	/** Task priority. */
	protected int mPriority;

	/** Number of max used for threads. */
	protected int mMaxThread;

	/** Task start time. */
	protected int mStartTime;

	protected File path;


	public File getPath() {
		return path;
	}


	public DownloadTaskDescriptor setPath(File path) {
		this.path = path;
		return this;
	}


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
