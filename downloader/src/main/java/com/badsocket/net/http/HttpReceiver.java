package com.badsocket.net.http;

import com.badsocket.net.AbstractLimitableReceiver;
import com.badsocket.net.Receiver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Download data from URL, based HTTP protocol.
 *
 * @since 2015/11/29
 */
public class HttpReceiver extends AbstractLimitableReceiver {
	/**
	 * Chunked of key value for http header Transfer-Encoding.
	 */
	public final static String CHUNKED = "chunked";

	/**
	 * Http header with key "Transfer-Encoding"?
	 */
	protected boolean isChunked = false;

	/**
	 * Flag of content compressed by gzip.
	 */
	protected boolean isGzip = false;

	/**
	 * Size of current downloading chunked block.
	 */
	protected long mCurrentChunkSize = 0;

	protected long lastChunkRemainSize = 0;

	protected HttpHeader mHeader;

	protected HttpResponse httpResponse;

	protected FileChannel fileChannel;

	public HttpReceiver(BaseHttpRequest req, OutputStream os) throws IOException {
		super(req.socket().getInputStream(), os);
		httpResponse = (HttpResponse) req.response();
		isChunked = httpResponse.isChunked();
//		dataOffsetBegin = d.getRange() != null ? d.getRange().start : 0;
//		dataOffsetEnd = isChunked ? -1 : dataOffsetBegin + d.getRange().getRange() + 1;
	}

	/**
	 * Download data as chunk.
	 *
	 * @return Parsed chunk data.
	 */
	protected void receiveChunked(long size) throws IOException {
		long sizeToReceive = 0;
		boolean receiveAll = size <= 0;

		while (!isStop) {
			if (!receiveAll && size <= 0) {
				break;
			}
			if (lastChunkRemainSize > 0) {
				sizeToReceive = Math.min(size, lastChunkRemainSize);
				size -= sizeToReceive;
				lastChunkRemainSize -= sizeToReceive;
			}
			else {
				mCurrentChunkSize = getChunkSize(mInputStream);
				if (mCurrentChunkSize == 0) {
					break;
				}
				sizeToReceive = mCurrentChunkSize;
				if (!receiveAll) {
					sizeToReceive = receiveAll ? mCurrentChunkSize : Math.min(mCurrentChunkSize, size);
					size -= sizeToReceive;
					lastChunkRemainSize = mCurrentChunkSize - sizeToReceive;
				}
			}

			receiveAndWrite(sizeToReceive);
		}

		finishReceive();
	}

	/**
	 * Gets chunk size from special InputStream.
	 *
	 * @param is Special InputStream.
	 * @return Chunk size.
	 * @throws IOException
	 */
	protected int getChunkSize(InputStream is) throws IOException {
		byte aByte;
		int matchCount = 0, byteCount = 0, emptyLine = 0;
		byte[] buff = new byte[BUFFER_SIZE],
			   crlf = {0x0D, 0x0A}; // \r\n

		while (Receiver.END_OF_STREAM != (aByte = (byte) is.read())) {
			if (aByte == crlf[matchCount]) {
				if (++matchCount == 2) {
					if (byteCount != 0) {
						return Integer.parseInt(new String(buff, 0, byteCount), 16);
					}
					else {
						emptyLine++;
					}
					byteCount = 0;
					matchCount = 0;
				}
			}
			else {
				matchCount = 0;
				buff[byteCount++] = aByte;
			}
		}

		return emptyLine > 1 ? 0 : -1;
	}

	/**
	 * To downloading data from source, and save data to somewhere.
	 *
	 * @param size Size of will downloading.
	 */
	public void receive(long size) throws IOException {
		if (size == 0)
			throw new IllegalArgumentException("Size of receive is illegal!");

		if (isChunked) {
			receiveChunked(size);
		}
		else {
			super.receive(size);
		}
	}

	public void receive() throws IOException {
		receive(-1);
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}
}
