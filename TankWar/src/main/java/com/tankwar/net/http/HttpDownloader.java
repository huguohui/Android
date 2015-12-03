package com.tankwar.net.http;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Arrays;

import com.tankwar.net.Downloader;
import com.tankwar.net.Parser;
import com.tankwar.net.Requester;

/**
 * Download data from URL, based HTTP protocol.
 * @since 2015/11/29
 */
public class HttpDownloader extends Downloader {
	/** The input stream. */
	private InputStream mInputStream;


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
		mInputStream = requester.getSocket().getInputStream();
	}


	/**
	 * Start download data.
	 */
	@Override
	public void download() throws IOException {
		if (getSaveTo() == null)
			throw new FileNotFoundException("Not special file to save data!");

		download(getSaveTo());
	}


	/**
	 * Download data form stream.
	 */
	public void download(String file) throws IOException {
		String transferEncoding = getRequester().getHeader().get(Http.TRANSFER_ENCODING);
		OutputStream fos = new FileOutputStream(file);

		if (transferEncoding != null && transferEncoding.equals("chunked")) {
			downloadChunked(fos);
		}else{
			int len;
			if (getRequester().getHeader().get(Http.CONTENT_LENGTH) != null) {
				len = Integer.parseInt(getRequester().getHeader().get(Http.CONTENT_LENGTH));
				fos.write(receive(getRequester().getSocket().getInputStream(), len));
			}else{
				InputStream is = getRequester().getSocket().getInputStream();
				byte[] buff = new byte[BUFFER_SIZE];
				int l = 0;
				while(-1 != (l = is.read(buff))) {
					fos.write(buff, 0, l);
				}
			}
			
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}
	}


	/**
	 * Download data as chunk.
	 */
	private void downloadChunked(OutputStream fos) throws IOException {
		HttpChunkedParser chunkedParser = new HttpChunkedParser();
		byte[] buff;
		while((buff = chunkedParser.parse(
				getRequester().getSocket().getInputStream())) != null) {
			if (buff[0] == HttpChunkedParser.CHUNKED_END_FLAG) {
				getRequester().getSocket().shutdownInput();
				getRequester().getSocket().shutdownOutput();
				getRequester().getSocket().close();
				System.out.println("[**] Download finished!");
				break;
			}

			System.out.println("[--] Downloaded: " + getDownloadedLength());
			setDownloadedLength(getDownloadedLength() + buff.length);
			fos.write(buff);
		}

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
		return (byte) mInputStream.read();
	}

	/**
	 * To receiving data from source, and save data to somewhere.
	 *
	 * @param source Data source.
	 * @param size
	 */
	@Override
	public byte[] receive(InputStream source, int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");
		if (getLength() > 0 && size + getDownloadedLength() > getLength())
			size = (int)(getLength() - getDownloadedLength());

		byte[] buff = new byte[BUFFER_SIZE];
		byte[] chunk = new byte[size];
		int count = 0, read = 0;
		while(count < size) {
			if (source.available() >= BUFFER_SIZE || source.available() >= size - count) {
				read = source.read(buff, 0, count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE);
				System.arraycopy(buff, 0, chunk, count, read);
				count += read;
			}
		}
		
		setDownloadedLength(getDownloadedLength() + size);
		return chunk;
	}

	/**
	 * To receiving data from source.
	 *  @param source Data source.
	 * @param size*/
	@Override
	public char[] receive(Reader source, int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");
		if (getLength() > 0 && size + getDownloadedLength() > getLength())
			size = (int)(getLength() - getDownloadedLength());

		char[] buff = new char[BUFFER_SIZE];
		char[] chunk = new char[size];
		int count = 0, read = 0;
		while(count < size) {
			read = source.read(buff, 0, count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE);
			System.arraycopy(buff, 0, chunk, count, read);
			count += read;
			System.out.println("Received: " + count);
		}
		
		setDownloadedLength(getDownloadedLength() + size);
		return chunk;
	}


	/**
	 * The Chunked content parser.
	 * @since 2015/12/2
	 */
	public class HttpChunkedParser implements Parser {
		/** Chunked end flag. */
		public final static byte CHUNKED_END_FLAG = 0x0;

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
			byte[] buff = new byte[BUFFER_SIZE];
			byte[] crlf = {0xd, 0xa};
			int matchCount = 0;
			int lineCount = 0;
			
			byte aByte;
			while(-1 != (aByte = (byte)data.read())) {
				if (aByte == crlf[matchCount])
					matchCount++;
				else
					matchCount = 0;

				if (matchCount == crlf.length) {
					if (lineCount > 0) {
						if (isHex(new String(buff, 0, lineCount))) {
							int len = Integer.parseInt(new String(buff, 0, lineCount), 16);
							if (len > 0) {
								buff = receive(data, len);
								return buff;
							}else{
								return new byte[]{CHUNKED_END_FLAG};
							}
						}
					}

					lineCount = 0;
					matchCount = 0;
					continue;
				}

				if (matchCount == 0) {
					buff[lineCount] = aByte;
					if (++lineCount >= buff.length){
						throw new RuntimeException("The algorithm is has worng!");
					}
				}
			}
			return null;
		}


		public byte[] trimContent(byte[] data) {
			int offStart = 0;
			int offEnd = 0;
			byte[] crlf = {0xa, 0xd};
			int matchCount = 0;

			for (int i = 0; i < data.length; i++) {
				if (data[i] == crlf[matchCount])
					matchCount++;
				else
					matchCount = 0;

				if (matchCount == crlf.length) {
					offStart = i + 1;
					break;
				}
			}

			return Arrays.copyOfRange(data, offStart, data.length);
		}


		public boolean isHex(String hex) {
			if (hex == null || hex.length() == 0) return false;

			byte[] chars = hex.getBytes();
			for (int i = 0; i < chars.length; i++) {
				if (!((chars[i] >= 48 && chars[i] <= 57) || (chars[i] >= 65 && chars[i] <= 90)
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
}
