package com.downloader.net;

import com.downloader.engine.Worker;
import com.downloader.io.DataWritable;

import java.io.InputStream;


/**
 * Download some data form a place.
 */
public abstract class AbstractReceiver implements Receiver {
	/** Default buffer size. */
	public final static int BUFFER_SIZE = 1024 << 3;

	/** The requester object. */
	protected InputStream mInputStream;

	/** Writable of receiver. */
	protected DataWritable mWritable;

	/** Is finished? */
	protected boolean isFinished = false;

	/** Received data length. */
	protected long mReceivedLength;

	protected Worker mWorker;

	/** Is stop receive? */
	protected boolean isStop = false;

	protected OnFinishedListener onFinishedListener;

	protected OnStopListener onStopListener;

	
	/**
	 * Construct a downloader by requester.
	 * @param is A {@link AbstractRequest}.
	 */
	public AbstractReceiver(InputStream is, DataWritable writable, Worker worker) {
		mInputStream = is;
		mWritable = writable;
		mWorker = worker;
	}


	protected AbstractReceiver() {
	}


	public InputStream getInputStream() {
		return mInputStream;
	}


	public long getReceivedLength() {
		return mReceivedLength;
	}


	public boolean isFinished() {
		return isFinished;
	}


	public boolean isStop() {
		return isStop;
	}


	public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
		this.onFinishedListener = onFinishedListener;
	}


	public void setOnStopListener(OnStopListener onStopListener) {
		this.onStopListener = onStopListener;
	}
}
