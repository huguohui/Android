package com.downloader.http;


import com.downloader.base.SocketReceiver;

import java.io.IOException;
import java.io.InputStream;

/**
 * Download data from URL, based HTTP protocol.
 * @since 2015/11/29
 */
public class HttpReceiver extends SocketReceiver {
	/** Chunked of key value for http header Transfer-Encoding. */
	public final static String CHUNKED = "chunked";
	
	/** Http header with key "Transfer-Encoding"? */     
	private boolean isChunked = false;
	
	/** Flag of content compressed by gzip. */
	private boolean isGzip = false;
	
	/** Is stop receive? */
	private boolean isStop = false;
	
	/** Is receives portal data? */
	private boolean isPortal = false;

	/** The length of data. */
	private long mLength = -1;

	/** The length of downloaded data. */
	private long mReceivedLength;

	/** Chunked parser. */
	private HttpChunkedParser mChunkedParser = new HttpChunkedParser();

	
	/**
	 * Construct a http downloader object.
	 *  @param is A {@link InputStream}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(InputStream is, Range r) throws IOException {
		super(is);
		setPortal(r != null);
	}
	

	/**
	 * Construct a http downloader object.
	 *  @param is A {@link InputStream}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(InputStream is) throws IOException {
		this(is, null);
	}


	/**
	 * Download data as chunk.
	 * @return Parsed chunk data.
	 */
	private byte[] receiveChunked() throws IOException {
		return mChunkedParser.parse(mInputStream);
	}


	/**
	 * To receiving data from source, and save data to somewhere.
	 * @param size Size of will receiving.
	 */
	@Override
	public synchronized void receive(int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("Size of receive is illegal!");
		
		if (getLength() > 0 && size + getReceivedLength() > getLength())
			size = (int) (getLength() - getReceivedLength());

		if (isChunked)
			receiveChunked();
		else
			super.receive(size);
	}


	private long getReceivedLength() {
		return mReceivedLength;
	}


	private void setReceivedLength(long receivedLength) {
		mReceivedLength = receivedLength;
	}


	private long getLength() {
		return mLength;
	}


	private void setLength(long length) {
		mLength = length;
	}
	

	public boolean isPortal() {
		return isPortal;
	}


	public void setPortal(boolean isPortal) {
		this.isPortal = isPortal;
	}
}
