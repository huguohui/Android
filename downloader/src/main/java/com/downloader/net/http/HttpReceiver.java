package com.downloader.net.http;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import com.downloader.net.AbsReceiver;
import com.downloader.net.Request;

/**
 * Download data from URL, based HTTP protocol.
 * @since 2015/11/29
 */
public class HttpReceiver extends AbsReceiver {
	/** Chunked of key value for http header Transfer-Encoding. */
	public final static String CHUNKED = "chunked";
	
	/** Http header with key "Transfer-Encoding"? */     
	private boolean isChunked = false;
	
	/** Is stop receive? */
	private boolean isStop = false;
	
	private HttpChunkedParser mChunkedParser = new HttpChunkedParser();


	/**
	 * Construct a http downloader object.
	 *  @param request A {@link Request}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(Request request) throws IOException {
		super(request);
		if (getRequest().getHeader().get(Http.CONTENT_LENGTH) != null)
			setLength(Long.parseLong(getRequest().getHeader().get(Http.CONTENT_LENGTH)));

		setDownloadedLength(0);
		setDataSource(request.getSocket().getInputStream());
		isChunked = CHUNKED.equals(request.getHeader().get(Http.TRANSFER_ENCODING));
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
		int count = 0, read = 0, freeLoop = 0;

		while(count < size) {
			int available = source.available();
			byte[] buff = new byte[BUFFER_SIZE];

			if (available == 0 && freeLoop++ < 100) {
				try { Thread.sleep(1); } catch( Exception ex ) {
					ex.printStackTrace();
				}
				continue;
			}

			if (END_OF_STREAM == (read = source.read(buff, 0,
					count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE))) {
				if (count != size) {
					byte[] data = new byte[count];
					System.arraycopy(chunk, 0, data, 0, count - 1);
					return data;
				}
				
				return null;
			}
			
			System.arraycopy(buff, 0, chunk, count, read);
			count += read;
			invokeListener("onReceive");
			freeLoop = 0;
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
		State state = getState(); 
		if (state.equals(State.exceptional) || state.equals(State.finished))
			return null;

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


		char[] buff = new char[BUFFER_SIZE];
		char[] chunk = new char[size];
		int count = 0, read = 0;

		while(count < size) {
			if (0 >= (read = source.read(buff, 0, count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE)))
				return null;

			System.arraycopy(buff, 0, chunk, count, count + read > size ? size - count : read);
			count += read;
			invokeListener("onReceive");
		}

		return chunk;
	}
	
	
	/**
	 * Keep receving data from stream.
	 * @throws IOException If exception.
	 */
	private void receiveAndSave() {
		byte[] buff;
		
		try {
			buff = receive(getDataSource(), BUFFER_SIZE);
			if (buff != null)
				getSaveTo().write(buff);
			else
				finishReceive();
		} catch(IOException e) {
			setState(State.exceptional);
			e.printStackTrace();
		}
	}
		
	
	/**
	 * Finish to receiving data.
	 */
	private void finishReceive() {
		OutputStream os = getSaveTo();
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


	/** 
	 * Run in a new thread to receiving data.
	 */
	@Override
	public void run() {
		while(!isStop) {
			switch(getState()) {
				case receiving:
					receiveAndSave();
					break;
				case stoped:
				case finished:
				case exceptional:
					isStop = true;
					break;
				case paused:
					synchronized(this) {
						try { wait(); } catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					break;
			}
		}
	}
	
	
	/**
	 * To starting receiving data.
	 */
	public void start() {
		super.start();
		Thread thread = new Thread(this);
		thread.start();
		setThread(thread);
	}
	
	
	/**
	 * To pausing receiving data.
	 */
	public void pause() {
		super.pause();
	}
}
