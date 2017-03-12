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

	/** Buffer for receiver. */
	private byte[] mBuffer = new byte[0x100000];

	
	/**
	 * Construct a http downloader object.
	 *  @param is A {@link InputStream}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(InputStream is, Writable w, Range r) throws IOException {
		super(is, w);
		setPortal(r != null);
	}
	

	/**
	 * Construct a http downloader object.
	 *  @param is A {@link InputStream}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(InputStream is, Writable w) throws IOException {
		this(is, w, null);
	}


	/**
	 * Download data as chunk.
	 * @return Parsed chunk data.
	 */
	private byte[] receiveChunked() throws IOException {
		byte[] buff = new byte[AbstractReceiver.BUFFER_SIZE];
		int chunkSize = 0;
		while((chunkSize = getChunkSize(mInputStream)) > 0) {

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
