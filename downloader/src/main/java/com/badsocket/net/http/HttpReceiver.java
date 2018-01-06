package com.badsocket.net.http;


import com.badsocket.io.writer.ConcurrentWriter;
import com.badsocket.io.writer.Writer;
import com.badsocket.net.AbstractReceiver;
import com.badsocket.net.Receiver;

import java.io.IOException;
import java.io.InputStream;

/**
 * Download data from URL, based HTTP protocol.
 * @since 2015/11/29
 */
public class HttpReceiver extends AbstractReceiver {
	/** Chunked of key value for http header Transfer-Encoding. */
	public final static String CHUNKED = "chunked";

	/** Http header with key "Transfer-Encoding"? */
	protected boolean isChunked = false;

	/** Flag of content compressed by gzip. */
	protected boolean isGzip = false;

	/** Buffer for receiver. */
	protected byte[] mBuffer = new byte[0x100000];

	/** Size of current downloading chunked block. */
	protected long mCurrentChunkedSize = 0;

	protected HttpHeader mHeader;

	protected HttpResponse httpResponse;

	protected ConcurrentWriter fileWriter;



	public HttpReceiver(BaseHttpRequest d, Writer w) throws IOException {
		super(d.socket().getInputStream(), w);
		fileWriter = (ConcurrentWriter) w;
		httpResponse = (HttpResponse) d.response();
		isChunked = httpResponse.isChunked();
		mSizeWillReceive = isChunked ? -1 : httpResponse.getContentLength();
		dataOffsetBegin = d.getRange() != null ? d.getRange().start : 0;
		dataOffsetEnd = isChunked ? -1 : dataOffsetBegin + d.getRange().getRange() + 1;
	}


	/**
	 * Download data as chunk.
	 * @return Parsed chunk data.
	 */
	protected void receiveChunked(long size) throws IOException {
		if (size < 0) {
			while(!isStop && mCurrentChunkedSize > 0
					|| (mCurrentChunkedSize = getChunkSize(mInputStream)) != 0) {
				receiveAndWrite(mCurrentChunkedSize);
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
				receiveAndWrite(willToReceiving);
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
		if (data == null) {
			return;
		}
		if (dataOffsetBegin != -1) {
			fileWriter.write(dataOffsetBegin, mReceivedLength - data.length, data);
			return;
		}

		super.writeData(data);
	}


	protected void flushWriter() throws IOException {
		fileWriter.flushBuffer(dataOffsetBegin);
	}


	/**
	 * To downloading data from source, and save data to somewhere.
	 * @param size Size of will downloading.
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


	public void receive(long size) throws IOException {
		mSizeWillReceive = size > 0 ? size : mSizeWillReceive;
		receiveData(mSizeWillReceive);
	}


	public void receive() throws IOException {
		receive(-1);
	}


	public HttpResponse getHttpResponse() {
		return httpResponse;
	}


	public void stop()  {
		isStop = true;
	}


	@Override
	public boolean isStoped() {
		return false;
	}


	public Writer getFileWriter() {
		return fileWriter;
	}


	public HttpReceiver setFileWriter(Writer fileWriter) {
		this.fileWriter = (ConcurrentWriter) fileWriter;
		return this;
	}
}
