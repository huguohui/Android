package com.downloader.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Download some data form a place.
 */
public abstract class Downloader implements Receiver {
	/** The requester object. */
	private Requester mRequester;

	/** The length of data. */
	private long mLength;

	/** The length of downloaded data. */
	private long mDownloadedLength;

	/** The file name of saving download. */
	private OutputStream mSaveTo;
	
	/** The download start time. */
	private long mStartTime;
	
	/** The download state. */
	private State mState = State.ready;
	
	/** The download finished time. */
	private long mFinishedTime;
	
	/** Download is finished? */
	private boolean mIsFinished = false;
	
	/** Datasource of downloading. */
	private InputStream mDataSource = null;
	
	/** The download states. */
	public enum State {
		ready, downloading, paused, stoped, finished
	}

	public final static int BUFFER_SIZE = 1024 << 3;


	/**
	 * Construct a downloader by requester.
	 * @param requester A {@link Requester}.
	 */
	public Downloader(Requester requester) {
		mRequester = requester;
		mStartTime = System.currentTimeMillis();
	}
	
	
	/**
	 * Starts to downloading.
	 */
	public abstract void start();
	
	
	/**
	 * Pauses task of downloading.
	 */
	public abstract void pause();
	
	
	/**
	 * Resumes task of downloading.
	 */
	public abstract void resume();
	
	
	/**
	 * Stops task of downloading.
	 */
	public abstract void stop();
	

	/**
	 * To downloading data.
	 */
	public abstract void download() throws IOException;


	/**
	 * Download data form stream to special position.
	 * @param to Where to save data.
	 */
	public abstract void download(OutputStream to) throws IOException;


	public Requester getRequester() {
		return mRequester;
	}

	public void setRequester(Requester requester) {
		mRequester = requester;
	}

	public long getLength() {
		return mLength;
	}

	public synchronized void setLength(long length) {
		mLength = length;
	}

	public long getDownloadedLength() {
		return mDownloadedLength;
	}

	public synchronized void setDownloadedLength(long downloadedLength) {
		mDownloadedLength = downloadedLength;
	}

	public OutputStream getSaveTo() {
		return mSaveTo;
	}

	public synchronized void setSaveTo(OutputStream saveTo) {
		mSaveTo = saveTo;
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
	
	
	public InputStream getDataSource() {
		return mDataSource;
	}


	public void setDataSource(InputStream dataSource) throws IOException {
		if (dataSource == null) 
			throw new IOException("The special data souce can't null!");

		mDataSource = dataSource;
	}


	/**
	 * The listener of downloading.
	 */
	public interface Listener {
		/**
		 * Invokes on downloader start download.
		 * @param downloader The listenered downloader.
		 */
		void onStart(Downloader downloader);

		/**
		 * Invokes on downloader per receive.
		 * @param downloader The listenered downloader.
		 */
		void onReceive(Downloader downloader);

		/**
		 * Invokes on downloader pause download.
		 * @param downloader The listenered downloader.
		 */
		void onPause(Downloader downloader);

		/**
		 * Invokes on downloader resume download.
		 * @param downloader The listenered downloader.
		 */
		void onResume(Downloader downloader);

		/**
		 * Invokes on downloader finish download.
		 * @param downloader The listenered downloader.
		 */
		void onFinish(Downloader downloader);
	}
}
