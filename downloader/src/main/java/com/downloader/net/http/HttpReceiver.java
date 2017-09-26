package com.downloader.net.http;


import com.downloader.client.downloader.HttpDownloader;
import com.downloader.net.AbstractReceiver;
import com.downloader.net.Receiver;
import com.downloader.net.SocketReceiver;
import com.downloader.engine.Worker;
import com.downloader.io.ConcurrentFileWritable;

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

	/** Size of current started chunked block. */
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
	public HttpReceiver(HttpResponse d, ConcurrentFileWritable w, Worker worker) throws IOException {
		this(d, w, worker, -1);
	}


	public HttpReceiver(HttpResponse d, ConcurrentFileWritable w, Worker worker, long offsetDataBegin) throws IOException {
		super(d.getInputStream(), w, worker);
		mWritable = w;
		httpResponse = d;
		this.offsetDataBegin = offsetDataBegin;
		isChunked = d.isChunked();
		mSizeWillReceive = isChunked ? -1 : d.getContentLength();
	}


	/**
	 * Download data as chunk.
	 * @return Parsed chunk data.
	 */
	private void receiveChunked(long size) throws IOException {
		if (size < 0) {
			while(!isStop && mCurrentChunkedSize > 0 || (mCurrentChunkedSize = getChunkSize(mInputStream)) != 0) {
				receiveDataBySize(mCurrentChunkedSize);
				mCurrentChunkedSize = 0;
			}

			isFinished = !isStop;
		} else {
			while (!isStop && size > 0) {
				if (mCurrentChunkedSize <= 0) {
					if ((mCurrentChunkedSize = getChunkSize(mInputStream)) == 0) {
						isFinished = true;
						return;
					}
				}

				long willToReceiving = mCurrentChunkedSize;
				receiveDataBySize(willToReceiving);
				mCurrentChunkedSize = 0 ;
			}
		}

		finishReceive();
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


	protected void writeData(byte[] data) throws IOException {
		if (offsetDataBegin != -1) {
			mWritable.write(offsetDataBegin, mReceivedLength - data.length, data);
			return;
		}

		super.writeData(data);
	}


	/**
	 * To started data from source, and save data to somewhere.
	 * @param size Size of will started.
	 */
	protected void receiveData(long size) throws IOException {
		if (size == 0)
			throw new IllegalArgumentException("Size of receive is illegal!");

		if (isChunked) {
			receiveChunked(size);
		}
		else {
			if (size < 0)
				super.receive();
			else
				super.receive(size);
		}
	}


	/**
	 * To started data from source, and save data to somewhere.
	 */
	protected void receiveData() throws IOException {
		receiveData(mSizeWillReceive);
	}


	public synchronized void receive(long size) throws IOException {
		mSizeWillReceive = size > 0 ? size : mSizeWillReceive;
		mWorker.add(this);
	}


	public synchronized void receive() throws IOException {
		receive(-1);
	}


	public void work() throws IOException {
		receiveData();
	}


	public HttpResponse getHttpResponse() {
		return httpResponse;
	}


	public synchronized void Stop() throws IOException {
		isStop = true;
	}
}
