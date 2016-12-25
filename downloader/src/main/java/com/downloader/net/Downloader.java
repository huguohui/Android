package com.downloader.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Download some data form a place.
 */
public abstract class Downloader implements Receiver, Controlable {
	/** The requester object. */
	private Request mRequest;

	/** The length of data. */
	private long mLength = -1;

	/** The length of downloaded data. */
	private long mDownloadedLength;

	/** The file name of saving download. */
	private OutputStream mSaveTo;
	
	/** The download start time. */
	private long mStartTime;
	
	/** The download state. */
	private State mState = State.unstart;
	
	/** The download finished time. */
	private long mFinishedTime;
	
	/** Download is finished? */
	private boolean mIsFinished = false;
	
	/** Datasource of downloading. */
	private InputStream mDataSource = null;
	
	/** Listener of downloading state. */
	private Listener mDownloadListener = null;
	
	/** The download states. */
	public enum State {
		unstart, downloading, paused, stoped, finished, exceptional
	}

	public final static int BUFFER_SIZE = 1024 << 3;


	/**
	 * Construct a downloader by requester.
	 * @param requester A {@link Request}.
	 */
	public Downloader(Request requester) {
		mRequest = requester;
		mStartTime = System.currentTimeMillis();
		invokeListener("onStart");
	}
	
	
	/**
	 * Starts to downloading.
	 */
	public synchronized void start() {
		mState = State.downloading;
		invokeListener("onStart");
	}
	
	
	/**
	 * Pauses task of downloading.
	 */
	public synchronized void pause() {
		mState = State.paused;
		invokeListener("onPause");
	}
	
	
	/**
	 * Resumes task of downloading.
	 */
	public synchronized void resume() {
		mState = State.downloading;
		invokeListener("onPause");
	}
	
	
	/**
	 * Stops task of downloading.
	 */
	public synchronized void stop() {
		mState = State.stoped;
		invokeListener("onStop");
	}
	
	
	/**
	 * Invokes the special method of listener.
	 * @param name The name of listener method.
	 */
	protected synchronized void invokeListener(String name) {
		if (mDownloadListener == null)
			throw new RuntimeException("The listener is null!");

		try {
			Method method = mDownloadListener.getClass().getMethod(name, Downloader.class);
			method.invoke(mDownloadListener, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Download data to special OutputStream.
	 * @param to OutputStream where data will write to.
	 */
	public abstract void download(OutputStream os);
	
	
	/**
	 * Download data to special OutputStream.
	 */
	public abstract void download();


	public Request getRequest() {
		return mRequest;
	}

	public void setRequester(Request requester) {
		mRequest = requester;
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


	public Listener getDownloadListener() {
		return mDownloadListener;
	}


	public void setDownloadListener(Listener downloadListener) {
		if (downloadListener == null)
			return;

		mDownloadListener = downloadListener;
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
