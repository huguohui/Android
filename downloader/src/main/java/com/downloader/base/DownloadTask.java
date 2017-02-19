package com.downloader.base;

import java.net.URL;

/**
 * A task for downloading.
 * @since 2016/12/26 15:46
 */
public abstract class DownloadTask extends AbsTask {
	/** Receiver instance. */
	private AbsReceiver mReceiver = null;

	/** Url for download task. */
	private URL mUrl = null;
	
	/** Speed of download. */
	private int mSpeed = 0;
	
	/** Break-point support. */
	private boolean isBreakPointResume = false;
	
	/** Length of download task. */
	private long mLength = 0;
	
	/** Content type of task. */
	private String mContentType = "";
	
	
	/**
	 * Constructor for creating task.
	 * @param d The downloader.
	 */
	public DownloadTask(URL url) {
		mUrl = url;
	}
	
	public AbsReceiver getReceiver() {
		return mReceiver;
	}


	public void setReceiver(AbsReceiver receiver) {
		mReceiver = receiver;
	}


	public URL getUrl() {
		return mUrl;
	}


	public void setUrl(URL url) {
		mUrl = url;
	}

	public int getSpeed() {
		return mSpeed;
	}

	public void setSpeed(int speed) {
		mSpeed = speed;
	}

	public boolean isBreakPointResume() {
		return isBreakPointResume;
	}

	public synchronized void setBreakPointResume(boolean isBreakPointResume) {
		this.isBreakPointResume = isBreakPointResume;
	}

	public synchronized long getLength() {
		return mLength;
	}

	public synchronized void setLength(long length) {
		mLength = length;
	}

	public String getContentType() {
		return mContentType;
	}

	public synchronized void setContentType(String contentType) {
		mContentType = contentType;
	}
}
