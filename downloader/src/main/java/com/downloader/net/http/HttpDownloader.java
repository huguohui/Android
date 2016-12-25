package com.downloader.net.http;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import com.downloader.net.Downloader;
import com.downloader.net.Parser;
import com.downloader.net.Requester;

/**
 * Download data from URL, based HTTP protocol.
 * @since 2015/11/29
 */
public class HttpDownloader extends Downloader {


	/**
	 * Construct a http downloader object.
	 * @param requester A {@link Requester}.
	 * @throws IOException 
	 */
	public HttpDownloader(Requester requester) throws IOException {
		super(requester);
		if (getRequester().getHeader().get(Http.CONTENT_LENGTH) != null)
			setLength(Long.parseLong(getRequester().getHeader().get(Http.CONTENT_LENGTH)));

		setDownloadedLength(0);
		setDataSource(requester.getSocket().getInputStream());
	}


	/**
	 * To downloading data.
	 */
	@Override
	public synchronized void download() throws IOException {
		if (getSaveTo() == null)
			throw new IOException("");
		
		InputStream is = getRequester().getSocket().getInputStream();
		byte[] buff;
		long lastTime = System.currentTimeMillis();
		
//		while((buff = receive(is, BUFFER_SIZE * 100)) != null) {
//			os.write(buff);
//			mDownloadedLength += buff.length;
//			long usedTime = System.currentTimeMillis() - mStartTime;
//			if (System.currentTimeMillis() - lastTime > 1000) {
//				lastTime = System.currentTimeMillis();
//				System.out.print("[**] Downloaded: " + mDownloadedLength);
//				System.out.println(" " + mDownloadedLength / (usedTime / 1000) / 1024 + "KB/s");
//			}
//		}
//
//		System.out.println("[**] Total used time: " + (System.currentTimeMillis() - mStartTime) / 1000);
//		setState(State.finished);
//		setIsFinished(true);
//		os.flush();
//		os.close();
//		mRequester.close();
	}


	/**
	 * Download data form stream to special position.
	 */
	public synchronized void download(OutputStream to) throws IOException {
		String transferEncoding = getRequester().getHeader().get(Http.TRANSFER_ENCODING);
		if (to == null)
			throw new IOException("The OuputStream of scribing is null!");

		
	}


	/**
	 * Download data as chunk.
	 */
	private void downloadChunked(OutputStream to) throws IOException {
		HttpChunkedParser chunkedParser = new HttpChunkedParser();
		OutputStream fos = to;
		byte[] buff;

		while((buff = chunkedParser.parse(getDataSource())) != null) {
			setDownloadedLength(getDownloadedLength() + buff.length);
			System.out.println("[--] Downloaded: " + getDownloadedLength());
			fos.write(buff);
		}

		getRequester().close();
		setState(State.finished);
		setIsFinished(true);
		System.out.println("[**] Download finished!");
		fos.flush();
		fos.close();
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
	 * @param size
	 */
	@Override
	public synchronized byte[] receive(InputStream source, int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");
		if (getLength() > 0 && size + getDownloadedLength() > getLength())
			size = (int)(getLength() - getDownloadedLength());

		if (size <= 0) return null;

		byte[] buff = new byte[BUFFER_SIZE];
		byte[] chunk = new byte[size];
		int count = 0, read = 0, wait = 0;
		boolean dataAvailable = false;

		while(count < size) {
			int available = source.available();

			if (available >= BUFFER_SIZE || available >= size - count || dataAvailable) {
				if (0 >= (read = source.read(buff, 0, count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE)))
					return null;

				System.arraycopy(buff, 0, chunk, count, read);
				count += read;
				dataAvailable = false;
			}else{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				dataAvailable = available != 0;
			}
		}

		return chunk;
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
			size = (int)(getLength() - getDownloadedLength());

		if (size <= 0) return null;

		char[] buff = new char[BUFFER_SIZE];
		char[] chunk = new char[size];
		int count = 0, read = 0;

		while(count < size) {
			if (0 >= (read = source.read(buff, 0, count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE)))
				return null;

			System.arraycopy(buff, 0, chunk, count, read);
			count += read;
		}

		return chunk;
	}


	/**
	 * The Chunked content parser.
	 * @since 2015/12/2
	 */
	public class HttpChunkedParser implements Parser {
		/**
		 * Parse data to a kind of format.
		 *
		 * @param data Provided data.
		 * @return Some data.
		 */
		@Override
		public Object parse(byte[] data) {
			return null;
		}

		/**
		 * Parse a chunk from data stream.
		 *
		 * @param data Data stream.
		 * @return A chunk data.
		 */
		@Override
		public byte[] parse(InputStream data) throws IOException {
			int chunkSize = 0;

			while((chunkSize = getChunkSize(data)) != 0) {
				if (chunkSize > 0) {
					return receive(data, chunkSize);
				}
			}

			return null;
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
			byte[] buff = new byte[BUFFER_SIZE << 1], crlf = {0x0D, 0x0A};

			while(END_OF_STREAM != (aByte = (byte)is.read())) {
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
		

		public boolean isHex(String hex) {
			if (hex == null || hex.length() == 0) return false;

			byte[] chars = hex.getBytes();
			for (int i = 0; i < chars.length; i++) {
				if (!((chars[i] >= 48 && chars[i] <= 57)
						|| (chars[i] >= 65 && chars[i] <= 90)
						|| (chars[i] >= 97 && chars[i] <= 122)))
					return false;
			}
			return true;
		}


		/**
		 * Parse data to a kind of format.
		 *
		 * @param data Provided data.
		 * @return Some data.
		 */
		@Override
		public Object parse(Reader data) throws IOException {
			return null;
		}
	}


	@Override
	public void start() {
		
	}


	@Override
	public void pause() {
		
	}


	@Override
	public void resume() {
		
	}


	@Override
	public void stop() {
		
	}
}
