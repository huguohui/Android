package com.downloader.base;

import com.downloader.util.Writable;

import java.io.InputStream;


/**
 * Download some data form a place.
 */
public abstract class AbstractReceiver implements Receiver, Runnable {
	/** The requester object. */
	protected InputStream mInputStream;

	/** The thread of receiver. */
	protected Thread mThread = null;

	/** Range of data will to receiving. */
	protected AbstractReceiver.Range mRange = null;

	/** Writable of receiver. */
	protected Writable mWritable;

	/** Is finished? */
	protected boolean isFinished = false;

	/** Default buffer size. */
	public final static int BUFFER_SIZE = 1024 << 3;

	/** Received data length. */
	protected long mReceivedLength;


	/**
	 * Construct a downloader by requester.
	 * @param is A {@link AbstractRequest}.
	 */
	public AbstractReceiver(InputStream is, Writable writable) {
		this(is, writable, null);
	}
	
	
	/**
	 * Construct a downloader by requester.
	 * @param is A {@link AbstractRequest}.
	 * @param r Range of data will to receiving.
	 */
	public AbstractReceiver(InputStream is, Writable writable, Range r) {
		mInputStream = is;
		mWritable = writable;
		mRange = r;
	}


	protected AbstractReceiver() {
	}


	public Thread getThread() {
		return mThread;
	}


	public void setThread(Thread thread) {
		mThread = thread;
	}


	public Range getRange() {
		return mRange;
	}


	public void setRange(Range range) {
		mRange = range;
	}


	private InputStream getInputStream() {
		return mInputStream;
	}


	private void setInputStream(InputStream inputStream) {
		mInputStream = inputStream;
	}


	private long getReceivedLength() {
		return mReceivedLength;
	}


	private void setReceivedLength(long receivedLength) {
		mReceivedLength = receivedLength;
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
