package com.downloader.http;


import com.downloader.base.AbstractReceiver;
import com.downloader.base.Receiver;
import com.downloader.base.SocketReceiver;
import com.downloader.util.Writable;

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
	protected boolean isChunked = false;
	
	/** Flag of content compressed by gzip. */
	protected boolean isGzip = false;
	
	/** Is stop receive? */
	protected boolean isStop = false;
	
	/** Is receives portal data? */
	protected boolean isPortal = false;

	/** The length of data. */
	protected long mLength = -1;

	/** The length of downloaded data. */
	protected long mReceivedLength;

	/** Buffer for receiver. */
	protected byte[] mBuffer = new byte[0x100000];

	/** Size for next receiving. */
	protected int mSizeWillReceive = 0;

	/** Size of current receiving chunked block. */
	protected long mCurrentChunkedSize = 0;

	
	/**
	 * Construct a http downloader object.
	 *  @param d A {@link HttpDownloader}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(HttpDownloaer d, Writable w, Range r) throws IOException {
		super(d.getInputStream(), w);
		isPortal = r != null;
		isChunked = CHUNKED.equals(((HttpHeader) d.getHeader()).get(Http.TRANSFER_ENCODING));
	}
	

	/**
	 * Construct a http downloader object.
	 *  @param d A {@link HttpDownloader}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(HttpDownloader d, Writable w) throws IOException {
		this(d, w, null);
	}


	/**
	 * Download data as chunk.
	 * @return Parsed chunk data.
	 */
	private void receiveChunked(int size) throws IOException {
		mSizeWillReceive = size;
		while(mSizeWillReceive > 0) {
			if (mCurrentChunkedSize <= 0)
				if ((mCurrentChunkedSize = getChunkSize(mInputStream)) == 0) {
					isFinished = true;
					return;
				}
			}

			int willToReceiving = mCurrentChunkedSize < mSizeWillReceive ? mCurrentChunkedSize : mSizeWillReceive;
			super.receive(willToReceiving);
			mCurrentChunkedSize -= willToReceiving;
			mSizeWillReceive -= willToReceiving;
		}
	}


	/**
	 * Gets chunk size from special InputStream.
	 * @param is Special InputStream.
	 * @return Chunk size.
	 * @throws IOException
	 */
	private int getChunkSize(InputStream is) throws IOException {
		byte aByte;
		int matchCount = 0, byteCount = 0, emptyLine = 0;
		byte[] buff = new byte[AbstractReceiver.BUFFER_SIZE], crlf = {0x0D, 0x0A};

		while(Receiver.END_OF_STREAM != (aByte = (byte)is.read())) {
			if (aByte == crlf[matchCount]) {
				if (++matchCount == 2) {
					if (byteCount != 0)
						return Integer.parseInt(new String(buff, 0, byteCount), 16);
					else
						emptyLine++;

					byteCount = 0;
					matchCount = 0;
				}
			}else{
				matchCount = 0;
				buff[byteCount++] = aByte;
			}
		}

		return emptyLine > 1 ? 0 : -1;
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
			receiveChunked(size);
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
