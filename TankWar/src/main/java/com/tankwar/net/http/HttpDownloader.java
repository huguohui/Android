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
				fos.write(getDataByLength(getRequester().getSocket().getInputStream(), len));
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
			if (buff.equals(new byte[]{0, 0xd, 0xa}))
				break;
	
			setLength(getLength() + buff.length);
			setDownloadedLength(getDownloadedLength() + buff.length);
			fos.write(buff);
		}

		fos.flush();
		fos.close();
	}
	
	
	
	/**
	 * Download by length.
	 * @param len The special length.
	 * @return Data with special length.
	 * @throws IOException 
	 */
	public byte[] getDataByLength(InputStream data, int len) throws IOException {
		int count = 0, read = 0;
		byte[] chunk = new byte[len];
		byte[] buff = new byte[BUFFER_SIZE];
		
		while(count < len) {
			if (-1 == (read = data.read(buff))) break;

			if (count + read > len) read = len - count;
				System.arraycopy(buff, 0, chunk, count, read);
				
			count += read;
			System.out.println("received: " + count);
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
		if (size + getDownloadedLength() > getLength())
			size = (int)(getLength() - getDownloadedLength());

		byte[] buff = new byte[size];
		if (-1 == source.read(buff))
			return null;
		setDownloadedLength(getDownloadedLength() + size);
		return buff;
	}

	/**
	 * To receiving data from source.
	 *  @param source Data source.
	 * @param size*/
	@Override
	public char[] receive(Reader source, int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");
		if (size + getDownloadedLength() > getLength())
			size = (int)(getLength() - getDownloadedLength());

		char[] buff = new char[size];
		if (-1 == source.read(buff))
			return null;
		setDownloadedLength(getDownloadedLength() + size);
		return buff;
	}


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
						for (int i = 0; i < lineCount; i++) System.out.print((char)buff[i]);
						if (isHex(new String(buff, 0, lineCount))) {
							int len = Integer.parseInt(new String(buff, 0, lineCount), 16);
							System.out.println("is" + len);
							if (len > 0) {
								return getDataByLength(data, len);
							}else{
								System.out.println("ssssssssss");
								return new byte[]{0, 0xd, 0xa};
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
