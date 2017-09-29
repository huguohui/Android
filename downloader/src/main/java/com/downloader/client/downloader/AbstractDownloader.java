package com.downloader.client.downloader;


import com.downloader.engine.AbstractTaskInfo;
import com.downloader.engine.Controlable;
import com.downloader.net.AbstractHeader;
import com.downloader.net.Response;
import com.downloader.util.TimeUtil;
import com.downloader.util.StringUtil;

import java.io.IOException;
import java.net.URL;

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

	protected int totalThreads = 1;

	protected AbstractTaskInfo info;


	/** The download states. */
	public enum State {
		unstart, init, prepared, started, paused, stoped, finished, exceptional, waiting
	}


	public AbstractDownloader() {
		mState = State.init;
	}


	/**
	 * Starts to downloading.
	 * @throws IOException
	 */
	public synchronized void start() throws Exception {
		mStartTime = TimeUtil.getMillisTime();
		setState(State.started);
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
	public synchronized void resume() {
		setState(State.started);
	}


	/**
	 * Stops to downloading.
	 */
	public synchronized void stop() {
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


	public int getTotalThreads() {
		return totalThreads;
	}


	public AbstractTaskInfo getInfo() {
		return info;
	}
}
