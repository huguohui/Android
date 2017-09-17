package com.downloader.http;


import com.downloader.base.AbstractReceiver;
import com.downloader.base.Receiver;
import com.downloader.base.SocketReceiver;
import com.downloader.util.ConcurrentFileWritable;
import com.downloader.util.FileWritable;

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

	/** Buffer for receiver. */
	protected byte[] mBuffer = new byte[0x100000];

	/** Size of current receiving chunked block. */
	protected long mCurrentChunkedSize = 0;

	protected HttpHeader mHeader;

	protected HttpResponse httpResponse;

	protected ConcurrentFileWritable mWritable;

	protected long offsetDataBegin = -1;

	
	/**
	 * Construct a http downloader object.
	 *  @param d A {@link HttpDownloader}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(HttpResponse d, ConcurrentFileWritable w) throws IOException {
		this(d, w, -1);
	}


	public HttpReceiver(HttpResponse d, ConcurrentFileWritable w, long offsetDataBegin) throws IOException {
		super(d.getInputStream(), (FileWritable) w);
		mWritable = w;
		httpResponse = d;
		this.offsetDataBegin = offsetDataBegin;
		mSizeWillReceive = d.getContentLength();
		isChunked = Http.CHUNKED.equalsIgnoreCase(d.getTransferEncoding());
	}


	/**
	 * Download data as chunk.
	 * @return Parsed chunk data.
	 */
	private void receiveChunked(long size) throws IOException {
		if (size < 0) {
			while(!isStop && mCurrentChunkedSize > 0 || (mCurrentChunkedSize = getChunkSize(mInputStream)) != 0) {
				receiveData(mCurrentChunkedSize);
				mCurrentChunkedSize = 0;
			}

			isFinished = !isStop && true;
		} else {
			while (!isStop && size > 0) {
				if (mCurrentChunkedSize <= 0) {
					if ((mCurrentChunkedSize = getChunkSize(mInputStream)) == 0) {
						isFinished = true;
						return;
					}
				}

				long willToReceiving = mCurrentChunkedSize /*< mSizeWillReceive ? mCurrentChunkedSize : mSizeWillReceive*/;
				receiveData(willToReceiving);
				mCurrentChunkedSize = 0 /*-= willToReceiving*/;
//				mSizeWillReceive -= willToReceiving;
			}
		}

		invokeListener();
	}


	/**
	 * Gets chunk size from special InputStream.
	 * @param is Special InputStream.
	 * @return Chunk size.
	 * @throws IOException
	 */
	protected int getChunkSize(InputStream is) throws IOException {
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
	public synchronized void receive(long size) throws IOException {
		if (size == 0)
			throw new IllegalArgumentException("Size of receive is illegal!");

		if (isChunked)
			receiveChunked(size);
		else
			super.receive(size > 0 ? size : mSizeWillReceive);
	}


	protected void writeData(byte[] data) throws IOException {
		if (offsetDataBegin != -1) {
			mWritable.write(offsetDataBegin, mReceivedLength - data.length, data);
			return;
		}

		super.writeData(data);
	}


	/**
	 * To receiving data from source, and save data to somewhere.
	 */
	@Override
	public synchronized void receive() throws IOException {
		receive(END_OF_STREAM);
	}


	public HttpResponse getHttpResponse() {
		return httpResponse;
	}


	public synchronized void Stop() throws IOException {
		isStop = true;
	}
}
