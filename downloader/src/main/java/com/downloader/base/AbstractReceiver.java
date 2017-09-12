package com.downloader.base;

import com.downloader.util.Writable;

import java.io.InputStream;


/**
 * Download some data form a place.
 */
public abstract class AbstractReceiver implements Receiver {
	/** Default buffer size. */
	public final static int BUFFER_SIZE = 1024 << 3;

	/** The requester object. */
	protected InputStream mInputStream;

	/** Range of data will to receiving. */
	protected Range mRange = null;

	/** Writable of receiver. */
	protected Writable mWritable;

	/** Is finished? */
	protected boolean isFinished = false;

	/** Received data length. */
	protected long mReceivedLength;

	/** Is stop receive? */
	protected boolean isStop = false;

	protected OnFinishedListener onFinishedListener;

	protected OnStopListener onStopListener;

	
	/**
	 * Construct a downloader by requester.
	 * @param is A {@link AbstractRequest}.
	 */
	public AbstractReceiver(InputStream is, Writable writable) {
		mInputStream = is;
		mWritable = writable;
	}


	protected AbstractReceiver() {
	}


	public Range getRange() {
		return mRange;
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


	/**
	 * A range of data.
	 */
	final public static class Range {
		/** Start offset. */
		public long start;
		
		/** End offset. */
		public long end;
	
		public Range(long s, long e) {
			if (s > e || e != 0)
				throw new IllegalArgumentException("The end must >= start and end must > 0!");

			this.start = s;
			this.end = e;
		}


		public long getRange() {
			return -~end - start;
		}
		
		
		public String toString() {
			return String.format("%d-%d", start, end);
		}
	}
}
