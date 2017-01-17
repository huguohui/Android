package com.downloader.http;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Arrays;

import com.AbsReceiver;
import com.Request;
import com.downloader.util.UrlUtil;

/**
 * Download data from URL, based HTTP protocol.
 * @since 2015/11/29
 */
public class HttpReceiver extends AbsReceiver {
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
	
	/** Chunked parser. */
	private HttpChunkedParser mChunkedParser = new HttpChunkedParser();


	/**
	 * Construct a http downloader object.
	 *  @param request A {@link Request}.
	 * @throws IOException If exception.
	 */
	public HttpReceiver(HttpRequest request) throws IOException {
		super(request);
		readResponse();
	}
	
	
	/**
	 * Prepare to start receiving data.
	 * @throws IOException 
	 */
	private void readResponse() throws IOException {
		HttpRequest request = (HttpRequest) getRequest();
		if (!request.isSend())
			return;
		
		getHeader().setContent(request.getSocket().getInputStream());
		isChunked = CHUNKED.equals(getHeader().get(Http.TRANSFER_ENCODING));
		isGzip = "Gzip".equals(getHeader().get(Http.CONTENT_ENCODING));
		if (getHeader().get(Http.LOCATION) != null) {
			request.reopen(UrlUtil.getFullUrl(request.getUrl(), getHeader().get(Http.LOCATION)));
			sendRequest();
		}
		
		if (getSaveTo() == null) {
			String fileName = UrlUtil.getFilename(request.getUrl()), disp = "";
			if ((disp = getHeader().get(Http.CONTENT_DISPOSITION)) != null && disp.length() != 0) {
				int off = 0;
				if ((off = disp.indexOf("filename")) != -1)
					fileName = disp.substring(off + 1);
			}

			setSaveTo(new FileOutputStream("D:\\" + fileName));
		}

		if (getHeader().get(Http.CONTENT_LENGTH) != null)
			setLength(Long.parseLong(getHeader().get(Http.CONTENT_LENGTH)));

		setReceivedLength(0);
		setDataSource(request.getSocket().getInputStream());
	}
	
	
	/**
	 * Send http request.
	 */
	private void sendRequest() throws IOException {
		ensureOpen();
		if (getRequest().isSend())
			throw new IOException("Can't send request becuase reqeust was sent!");
		
		getRequest().send();
		readResponse();
	}
	
	
	/**
	 * Ensure request was opened.
	 * @throws IOException 
	 */
	private void ensureOpen() throws IOException {
		Request rqst = getRequest(); 
		if (rqst == null || !rqst.isConnect() || Request.State.closed.equals(rqst.getState()))
			throw new IOException("Connection was aborted!");
	}
	
	
	/**
	 * Create a thread for receiver.
	 * @return A thread for receiver.
	 */
	private Thread createThread() {
		return new Thread(this);
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
				if (count != 0 && count != size)
					return Arrays.copyOfRange(buff, 0, count);

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
		
		if (getLength() > 0 && size + getReceivedLength() > getLength())
			size = (int) (getLength() - getReceivedLength());

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

		if (getLength() > 0 && size + getReceivedLength() > getLength())
			size = (int) (getLength() - getReceivedLength());


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
	public synchronized void run() {
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
				case waiting:
					try { wait(); } catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
			}
		}
	}
	
	
	/**
	 * Start to receiving data.
	 */
	public synchronized void startReceive() {
		setThread(createThread());
		getThread().start();
	}
	
	
	/**
	 * To starting receiving data.
	 * @throws IOException 
	 */
	public synchronized void start() throws IOException {
		super.start();
		if (!getRequest().isSend())
			sendRequest();

		startReceive();
	}
	
	
	/**
	 * To pausing receiving data.
	 * @throws IOException 
	 */
	public void pause() throws IOException {
		super.pause();
	}


	public HttpHeader getHeader() {
		return mHeader;
	}


	public void setHeader(HttpHeader header) {
		mHeader = header;
	}
}