package com.downloader.engine.downloader;


import com.downloader.engine.TaskInfo;
import com.downloader.util.TimeUtil;

import java.io.IOException;

public abstract class AbstractDownloader implements Downloader {
	/** The length of data. */
	protected long mLength = -1;

	/** The length of downloaded data. */
	protected long mDownloadedLength;

	/** The download start time. */
	protected long mStartTime;

	/** The download state. */
	protected DownloadState mState = DownloadState.unstart;

	/** The download finished time. */
	protected long mFinishedTime;

	/** Download is finished? */
	protected boolean mIsFinished = false;

	protected boolean mIsStop = false;

	protected long mDownloadTime = 0;

	protected int downloadThreads = 1;

	protected TaskInfo info;

	protected String path = "";

	protected OnDownloadFinishListener onDownloadFinishListener;

	protected OnDownloadStartListener onDownloadListener;


	public AbstractDownloader() {
		mState = DownloadState.initing;
	}


	/**
	 * Starts to downloading.
	 * @throws IOException
	 */
	public synchronized void start() throws Exception {
		mStartTime = TimeUtil.millisTime();
		setState(DownloadState.downloading);
	}


	/**
	 * Pauses task of downloading.
	 */
	public synchronized void pause() throws Exception {
		setState(DownloadState.paused);
	}


	/**
	 * Resumes task of downloading.
	 */
	public synchronized void resume() throws IOException {
		setState(DownloadState.downloading);
	}


	/**
	 * Stops to downloading.
	 */
	public synchronized void stop() throws IOException {
		setState(DownloadState.stoped);
		mIsStop = true;
	}

	public long getDownloadedLength() {
		return mDownloadedLength;
	}


	public long getLength() {
		return mLength;
	}


	public synchronized void setLength(long length) {
		mLength = length;
	}


	public long getStartTime() {
		return mStartTime;
	}


	public synchronized void setStartTime(long mStartTime) {
		this.mStartTime = mStartTime;
	}


	public DownloadState getState() {
		return mState;
	}


	public synchronized void setState(DownloadState mState) {
		this.mState = mState;
	}


	public long getFinishedTime() {
		return mFinishedTime;
	}


	public synchronized void setFinishedTime(long mFinishedTime) {
		this.mFinishedTime = mFinishedTime;
	}


	public boolean isFinished() {
		return mIsFinished;
	}


	public synchronized void setIsFinished(boolean mIsFinshed) {
		this.mIsFinished = mIsFinshed;
	}


	public long getDownloadTime() {
		return mDownloadTime;
	}


	public boolean isStop() {
		return mIsStop;
	}


	public int getDownloadThreads() {
		return downloadThreads;
	}


	public TaskInfo getInfo() {
		return info;
	}


	public String getPath() {
		return path;
	}


	public AbstractDownloader setPath(String path) {
		this.path = path;
		return this;
	}


	public OnDownloadFinishListener getOnDownloadFinishListener() {
		return onDownloadFinishListener;
	}


	public OnDownloadStartListener getOnDownloadListener() {
		return onDownloadListener;
	}
}
