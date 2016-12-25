package com.downloader.net.http;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import com.downloader.net.Downloader;
import com.downloader.net.Parser;
import com.downloader.net.Request;

/**
 * Download data from URL, based HTTP protocol.
 * @since 2015/11/29
 */
public class HttpDownloader extends Downloader {
	/** Http header with key "Transfer-Encoding"? */     
	private boolean isChunked = false;
	
	private HttpChunkedParser mChunkedParser = new HttpChunkedParser();


	/**
	 * Construct a http downloader object.
	 * @param request A {@link Request}.
	 * @throws IOException 
	 */
	public HttpDownloader(Request request) throws IOException {
		super(request);
		if (getRequest().getHeader().get(Http.CONTENT_LENGTH) != null)
			setLength(Long.parseLong(getRequest().getHeader().get(Http.CONTENT_LENGTH)));

		setDownloadedLength(0);
		setDataSource(request.getSocket().getInputStream());
	}

	
	/**
	 * Invokes the special method of listener.
	 * @param name The name of listener method.
	 */
	protected void invokeListener(String name) {
		try {
			super.invokeListener(name);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Download data as chunk.
	 * @return 
	 */
	private byte[] receiveChunked(InputStream is) throws IOException {
		return mChunkedParser.parse(is);
	}
	
	

	
	/**
	 * Receives data by size.
	 * @param size Size of receive.
	 * @throws IOException 
	 */
	private byte[] receiveData(InputStream source, int size) throws IOException {
		byte[] chunk = new byte[size];
		int count = 0, read = 0;
		
		while(count < size) {
			int available = source.available();
			byte[] buff = new byte[available];
			if (available != 0) {
				if (0 >= (read = source.read(buff, 0, available)))
					return null;

				System.arraycopy(buff, 0, chunk, count, read);
				count += read;
			}

			invokeListener("onReceive");
		}
		
		return chunk;
	}
	

	/**
	 * Receiving data.
	 *
	 * @return Received data by byte.
	 * @throws IOException      When I/O exception.
	 */
	@Override
	public byte receive() throws IOException {
		return (byte) getDataSource().read();
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
		
		if (getLength() > 0 && size + getDownloadedLength() > getLength())
			size = (int) (getLength() - getDownloadedLength());

		if (isChunked)
			return receiveChunked(source);

		return receiveData(source, size);
	}
	


	/**
	 * To receiving data from source.
	 * @param source Data source.
	 * @param size Size of will receiving.
	 */
	@Override
	public synchronized char[] receive(Reader source, int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");

		if (getLength() > 0 && size + getDownloadedLength() > getLength())
			size = (int) (getLength() - getDownloadedLength());

		if (size <= 0) return null;

		char[] buff = new char[BUFFER_SIZE];
		char[] chunk = new char[size];
		int count = 0, read = 0;

		while(count < size) {
			if (0 >= (read = source.read(buff, 0, count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE)))
				return null;

			System.arraycopy(buff, 0, chunk, count, read);
			count += read;
			invokeListener("onReceive");
		}

		return chunk;
	}
	


	/**
	 * To downloading data.
	 */
	@Override
	public synchronized void download(OutputStream os) {
		if (os == null)
			return;
		
		byte[] buff = null;
		InputStream is = getDataSource();

		try {
			while(getState().equals(State.downloading) && (buff = receive(is, BUFFER_SIZE * 100)) != null) {
				setDownloadedLength(getDownloadedLength() + buff.length);
				os.write(buff);
			}
		} catch(Exception e) {
			setState(State.exceptional);
			e.printStackTrace();
		} finally {
			if (buff == null && getState().equals(State.downloading)) {
				invokeListener("onFinish");
				setState(State.finished);
				setIsFinished(true);
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					setState(State.exceptional);
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * To downloading data.
	 */
	@Override
	public void download() {
		download(getSaveTo());
	}
	
	
	/**
	 * Starts download data.
	 */
	@Override
	public synchronized void start() {
		super.start();
		download();
	}
	
	
	/**
	 * Pauses download data.
	 */
	@Override
	public synchronized void pause() {
		super.pause();
	}
}
