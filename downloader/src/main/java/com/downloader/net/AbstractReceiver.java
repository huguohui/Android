package com.downloader.net;

import com.downloader.engine.Worker;
import com.downloader.io.FileWritable;

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
	protected FileWritable mFileWriter;

	/** Is finished? */
	protected boolean isFinished = false;

	/** Received data length. */
	protected volatile long mReceivedLength;

	protected Worker mWorker;

	/** Is stop receive? */
	protected boolean isStop = false;

	protected OnFinishedListener onFinishedListener;

	protected OnStopListener onStopListener;

	protected OnReceiveListener onReceiveListener;

	
	/**
	 * Construct a downloader by requester.
	 * @param is A {@link AbstractRequest}.
	 */
	public AbstractReceiver(InputStream is, FileWritable writable, Worker worker) {
		mInputStream = is;
		mFileWriter = writable;
		mWorker = worker;
	}


	protected AbstractReceiver() {
	}


	public InputStream getInputStream() {
		return mInputStream;
	}


	public synchronized long getReceivedLength() {
		return mReceivedLength;
	}


	public boolean isFinished() {
		return isFinished;
	}


	public boolean isStop() {
		return isStop;
	}


	public FileWritable getFileWriter() {
		return mFileWriter;
	}


	public AbstractReceiver setFileWriter(FileWritable fileWriter) {
		mFileWriter = fileWriter;
		return this;
	}


	public Worker getWorker() {
		return mWorker;
	}


	public AbstractReceiver setWorker(Worker worker) {
		mWorker = worker;
		return this;
	}


	public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
		this.onFinishedListener = onFinishedListener;
	}


	public void setOnStopListener(OnStopListener onStopListener) {
		this.onStopListener = onStopListener;
	}


	public AbstractReceiver setOnReceiveListener(OnReceiveListener onReceiveListener) {
		this.onReceiveListener = onReceiveListener;
		return this;
	}
}
