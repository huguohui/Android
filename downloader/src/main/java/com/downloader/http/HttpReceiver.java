package com.downloader.http;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Arrays;

import com.downloader.base.AbstractReceiver;
import com.downloader.base.AbstractRequest;
import com.downloader.base.SocketReceiver;
import com.downloader.manager.ThreadManager;
import com.downloader.util.UrlUtil;

import static com.downloader.base.AbstractDownloader.State.exceptional;
import static com.downloader.base.AbstractDownloader.State.stoped;
import static com.downloader.base.AbstractDownloader.State.waiting;

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
	
	/** Http response header. */
	private HttpHeader mHeader = new HttpHeader();
	
	/** Is receives portal data? */
	private boolean isPortal = false;
	
	/** Http status code. */
	private int mStatusCode = 0;
	
	/** File name of download. */
	private String mFileName = "";

	/** The length of data. */
	private long mLength = -1;

	/** The length of downloaded data. */
	private long mReceivedLength;

	/** Input stream what will to receiving. */
	private InputStream mInputStream;

	/** Chunked parser. */
	private HttpChunkedParser mChunkedParser = new HttpChunkedParser();

	
	/**
	 * Construct a http downloader object.
	 *  @param is A {@link InputStream}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(InputStream is, Range r) throws IOException {
		super(is, r);
		setPortal(r != null);
	}
	

	/**
	 * Construct a http downloader object.
	 *  @param is A {@link AbstractRequest}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(InputStream is) throws IOException {
		this(is, null);
	}

	/**
	 * Download data as chunk.
	 * @return 
	 */
	private byte[] receiveChunked(InputStream is) throws IOException {
		return mChunkedParser.parse(is);
	}


	/**
	 * To receiving data from source, and save data to somewhere.
	 *
	 * @param source Data source.
	 * @param size Size of will receiving.
	 */
	@Override
	public synchronized byte[] receive(InputStream source, int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("Size of receive is illegal!");
		
		if (getLength() > 0 && size + getReceivedLength() > getLength())
			size = (int) (getLength() - getReceivedLength());

		if (isChunked)
			return receiveChunked(source);

		return super.receive(source, size);
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


	public HttpHeader getHeader() {
		return mHeader;
	}


	public void setHeader(HttpHeader header) {
		mHeader = header;
	}


	public int getStatusCode() {
		return mStatusCode;
	}


	public void setStatusCode(int statusCode) {
		mStatusCode = statusCode;
	}
	

	public boolean isPortal() {
		return isPortal;
	}


	public void setPortal(boolean isPortal) {
		this.isPortal = isPortal;
	}


	public String getFileName() {
		return mFileName;
	}


	public void setFileName(String fileName) {
		mFileName = fileName;
	}
}
