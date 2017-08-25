package com.downloader.base;

import com.downloader.util.Writable;

import java.io.InputStream;


/**
 * Download some data form a place.
 */
public abstract class AbstractReceiver implements Receiver, Runnable {
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

	/** Is receives portal data? */
	protected boolean isPortal = false;


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
		isPortal = r != null;
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


	public boolean isPortal() {
		return isPortal;
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
