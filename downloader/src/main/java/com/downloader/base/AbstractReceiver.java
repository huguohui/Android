package com.downloader.base;

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

	/** Default buffer size. */
	public final static int BUFFER_SIZE = 1024 << 3;


	/**

	 * @param downloader The listenered downloader.
	 */ /**
	 * Construct a downloader by requester.
	 * @param is A {@link AbstractRequest}.
	 */
	public AbstractReceiver(InputStream is) {
		this(is, null);
	}
	
	
	/**
	 * Construct a downloader by requester.
	 * @param is A {@link AbstractRequest}.
	 * @param r Range of data will to receiving.
	 */
	public AbstractReceiver(InputStream is, Range r) {
		mInputStream = is;
		mRange = r;
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

	
	
	/**
	 * A range of data.
	 */
	public static class Range {
		/** Start offset. */
		public long start;
		
		/** End offset. */
		public long end;
	
		public Range(long s, long e) {
			if (s < 0 || e < 0 || s > e)
				throw new IllegalArgumentException("The start and end can't < 0 and end must >= start!");

			this.start = s;
			this.end = e;
		}
		
		
		public String toString() {
			return String.format("%d-%d", start, end - 1);
		}
	}
}
