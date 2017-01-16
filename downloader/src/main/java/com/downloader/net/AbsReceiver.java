package com.downloader.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import com.downloader.client.Controlable;
import com.downloader.util.ReflectUtil;
import com.downloader.util.StringUtil;


/**
 * Download some data form a place.
 */
public abstract class AbsReceiver implements Receive, Controlable, Runnable {
	/** The requester object. */
	private Request mRequest;

	/** The length of data. */
	private long mLength = -1;

	/** The length of downloaded data. */
	private long mReceivedLength;

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
	private Listener mListener = null;
	
	/** The thread of receiver. */
	private Thread mThread = null;
	
	/** Methods of listener. */
	private final String mListenerMethods[] = {
		"onStart", "onPause", "onResume", "onStop", "onFinish", "onReceive"
	};
	
	/** The download states. */
	public enum State {
		unstart, receiving, paused, stoped, finished, exceptional, waiting
	}

	/** Default buffer size. */
	public final static int BUFFER_SIZE = 1024 << 3;


	/**
	 * Construct a downloader by requester.
	 * @param requester A {@link Request}.
	 */
	public AbsReceiver(Request requester) {
		mRequest = requester;
	}
	
	
	/**
	 * Starts to downloading.
	 * @throws IOException 
	 */
	public synchronized void start() throws IOException {
		mState = State.receiving;
		invokeListener("onStart");
	}
	
	
	/**
	 * Pauses task of downloading.
	 */
	public synchronized void pause() throws IOException {
		mState = State.paused;
		invokeListener("onPauses");
	}
	
	
	/**
	 * Resumes task of downloading.
	 */
	public synchronized void resume() throws IOException {
		mState = State.receiving;
		invokeListener("onResume");
	}
	
	
	/**
	 * Stops to downloading.
	 */
	public synchronized void stop() throws IOException {
		mState = State.stoped;
		invokeListener("onStop");
	}
	
	
	/**
	 * Invokes the special method of listener.
	 * @param name The name of listener method.
	 */
	protected synchronized void invokeListener(String name) {
		if (mListener == null)
			return;
		
		switch(StringUtil.contains(mListenerMethods, name)) {
			case 0:		mListener.onStart(this); 	break;
			case 1:		mListener.onPause(this);	break;
			case 2:		mListener.onResume(this);	break;
			case 3:		mListener.onStop(this);		break;
			case 4:		mListener.onFinish(this);	break;
			case 5:		mListener.onReceive(this);	break;
			default: 	// Do nothing...
		}
	}


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

	public long getReceivedLength() {
		return mReceivedLength;
	}

	public synchronized void setReceivedLength(long downloadedLength) {
		mReceivedLength = downloadedLength;
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


	public Listener getReceiverListener() {
		return mListener;
	}


	public void setReceiverListener(Listener Listener) {
		if (Listener == null)
			return;

		mListener = Listener;
	}


	public Thread getThread() {
		return mThread;
	}


	public void setThread(Thread thread) {
		mThread = thread;
	}


	/**
	 * The listener of downloading.
	 */
	public interface Listener {
		/**
		 * Invokes on downloader start download.
		 * @param downloader The listenered downloader.
		 */
		void onStart(AbsReceiver downloader);

		/**
		 * Invokes on downloader stop download.
		 * @param downloader The listenered downloader.
		 */
		void onStop(AbsReceiver absReceiver);

		/**
		 * Invokes on downloader per receive.
		 * @param downloader The listenered downloader.
		 */
		void onReceive(AbsReceiver downloader);

		/**
		 * Invokes on downloader pause download.
		 * @param downloader The listenered downloader.
		 */
		void onPause(AbsReceiver downloader);

		/**
		 * Invokes on downloader resume download.
		 * @param downloader The listenered downloader.
		 */
		void onResume(AbsReceiver downloader);

		/**
		 * Invokes on downloader finish download.
		 * @param downloader The listenered downloader.
		 */
		void onFinish(AbsReceiver downloader);
	}
	
	
	/**
	 * A range of data.
	 */
	public static class Range {
		/** Start offset. */
		public int start;
		
		/** End offset. */
		public int end;
	
		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
}
