package com.badsocket.net.http;

import com.badsocket.net.AbstractReceiver;
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
public class HttpReceiver extends AbstractReceiver {
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
	 * Buffer for receiver.
	 */
	protected byte[] mBuffer = new byte[81920];

	/**
	 * Size of current downloading chunked block.
	 */
	protected int mCurrentChunkSize = 0;

	protected int lastChunkRemainData = 0;

	protected HttpHeader mHeader;

	protected HttpResponse httpResponse;

	protected FileChannel fileChannel;

	public HttpReceiver(BaseHttpRequest d, OutputStream os) throws IOException {
		super(d.socket().getInputStream(), os);
		httpResponse = (HttpResponse) d.response();
		isChunked = httpResponse.isChunked();
		dataOffsetBegin = d.getRange() != null ? d.getRange().start : 0;
		dataOffsetEnd = isChunked ? -1 : dataOffsetBegin + d.getRange().getRange() + 1;
	}

	/**
	 * Download data as chunk.
	 *
	 * @return Parsed chunk data.
	 */
	protected void receiveChunked(int size) throws IOException {
		boolean receiveAll = size == -1;
		int sizeToReceive = 0;
		while (!isStop) {
			if (size == 0) {
				break;
			}
			if (receiveAll) {
				mCurrentChunkSize = getChunkSize(mInputStream);
				sizeToReceive = mCurrentChunkSize;
			}
			else {
				if (lastChunkRemainData > 0) {
					sizeToReceive = Math.min(size, lastChunkRemainData);
					size -= sizeToReceive;
					lastChunkRemainData -= sizeToReceive;
				}
				else {
					mCurrentChunkSize = getChunkSize(mInputStream);
					sizeToReceive = Math.min(mCurrentChunkSize, size);
					size -= sizeToReceive;
					lastChunkRemainData = mCurrentChunkSize - sizeToReceive;
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
		byte[] buff = new byte[AbstractReceiver.BUFFER_SIZE], crlf = {0x0D, 0x0A};

		while (Receiver.END_OF_STREAM != (aByte = (byte) is.read())) {
			if (aByte == crlf[matchCount]) {
				if (++matchCount == 2) {
					if (byteCount != 0)
						return Integer.parseInt(new String(buff, 0, byteCount), 16);
					else
						emptyLine++;

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
	public void receive(int size) throws IOException {
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

	public void receive() throws IOException {
		receive(-1);
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}


	@Override
	public boolean stoped() {
		return false;
	}

}
