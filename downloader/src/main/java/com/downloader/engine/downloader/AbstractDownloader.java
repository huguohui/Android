package com.downloader.engine.downloader;


import com.downloader.engine.AbstractTaskInfo;
import com.downloader.engine.Controlable;
import com.downloader.util.TimeUtil;

import java.io.IOException;

public abstract class AbstractDownloader implements Controlable {
	/** The length of data. */
	protected long mLength = -1;

	/** The length of downloaded data. */
	protected long mDownloadedLength;

	/** The download start time. */
	protected long mStartTime;

	/** The download state. */
	protected State mState = State.unstart;

	/** The download finished time. */
	protected long mFinishedTime;

	/** Download is finished? */
	protected boolean mIsFinished = false;

	protected boolean mIsStop = false;

	protected long mDownloadTime = 0;

	protected int downloadThreads = 1;

	protected AbstractTaskInfo info;

	protected String path = "";

	protected OnDownloadFinishListener onDownloadFinishListener;

	protected OnDownloadStartListener onDownloadListener;


	/** The download states. */
	public enum State {
		unstart, initing, preparing, downloading, paused, stoped, finished, exceptional
	}


	public AbstractDownloader() {
		mState = State.initing;
	}


	/**
	 * Starts to downloading.
	 * @throws IOException
	 */
	public synchronized void start() throws Exception {
		mStartTime = TimeUtil.getMillisTime();
		setState(State.downloading);
	}


	/**
	 * Pauses task of downloading.
	 */
	public synchronized void pause() throws Exception {
		setState(State.paused);
	}


	/**
	 * Resumes task of downloading.
	 */
	public synchronized void resume() throws IOException {
		setState(State.downloading);
	}


	/**
	 * Stops to downloading.
	 */
	public synchronized void stop() throws IOException {
		setState(State.stoped);
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


	public State getState() {
		return mState;
	}


	public synchronized void setState(State mState) {
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


	public AbstractTaskInfo getInfo() {
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


	public interface OnDownloadStartListener {
		void onDownloadStart(AbstractDownloader d);
	}

	public interface OnDownloadFinishListener {
		void onDownloadFinish(AbstractDownloader d);
	}
}
