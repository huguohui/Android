package com.downloader.client.downloader;


import com.downloader.engine.Controlable;
import com.downloader.net.AbstractRequest;
import com.downloader.net.AbstractTaskInfo;
import com.downloader.util.TimeUtil;
import com.downloader.util.StringUtil;

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

	/** Listener of downloading state. */
	protected Listener mListener = null;

	protected long mDownloadTime = 0;


	/** Methods of listener. */
	private final String mListenerMethods[] = {
		"onStart", "onPause", "onResume", "onStop", "onFinish"
	};

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
		invokeListener("onStart");
	}


	/**
	 * Pauses task of downloading.
	 */
	public synchronized void pause() throws Exception {
		setState(State.paused);
		invokeListener("onPauses");
	}


	/**
	 * Resumes task of downloading.
	 */
	public synchronized void resume() {
		setState(State.started);
		invokeListener("onResume");
	}


	/**
	 * Stops to downloading.
	 */
	public synchronized void stop() {
		setState(State.stoped);
		mIsStop = true;
		invokeListener("onStop");
	}


	/**
	 * Invokes the special method of listener.
	 * @param name The name of listener method.
	 */
	protected synchronized void invokeListener(String name) {
		if (mListener == null)
			return;

		switch(StringUtil.inArray(mListenerMethods, name)) {
			case 0:		mListener.onStart(this); 	break;
			case 1:		mListener.onPause(this);	break;
			case 2:		mListener.onResume(this);	break;
			case 3:		mListener.onStop(this);		break;
			case 4:		mListener.onFinish(this);	break;
			default: 	// Do nothing...
		}
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


	public Listener getListener() {
		return mListener;
	}


	public long getDownloadTime() {
		return mDownloadTime;
	}


	public boolean isStop() {
		return mIsStop;
	}


	public void setListener(Listener Listener) {
		if (Listener == null)
			return;

		mListener = Listener;
	}


	/**
	 * The listener of downloading.
	 */
	public interface Listener {
		/**
		 * Invokes on downloader start download.
		 * @param abstractDownloader
		 */
		void onStart(AbstractDownloader abstractDownloader);


		/**
		 * Invokes on downloader stop download.
		 * @param absReceiver The listenered downloader.
		 */
		void onStop(AbstractDownloader absReceiver);


		/**
		 * Invokes on downloader pause download.
		 * @param downloader The listenered downloader.
		 */
		void onPause(AbstractDownloader downloader);


		/**
		 * Invokes on downloader resume download.
		 * @param downloader The listenered downloader.
		 */
		void onResume(AbstractDownloader downloader);


		/**
		 * Invokes on downloader finish download.
		 * @param downloader The listenered downloader.
		 */
		void onFinish(AbstractDownloader downloader);
	}
}
