package com.downloader.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.ConnectException;
import java.util.Arrays;

/**
 * Socket receiver can receive data from input stream.
 */
public class SocketReceiver extends AbstractReceiver {
	/**
	 * Construct a downloader by requester.
	 *
	 * @param is A {@link InputStream}.
	 */
	public SocketReceiver(InputStream is) {
		this(is, null);
	}


	/**
	 * Construct a downloader by requester.
	 * @param is A {@link AbstractRequest}.
	 * @param r Range of data will to receiving.
	 */
	public SocketReceiver(InputStream is, Range r) {
		super(is, r);
	}


	/**
	 * Receiving data.
	 *
	 * @return Received data by byte.
	 * @throws IOException      When I/O exception.
	 * @throws ConnectException When connection exception.
	 */
	@Override
	public byte receive() throws IOException {
		return receive(mInputStream, 1)[0];
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
			freeLoop = 0;
		}

		return chunk;
	}


	/**
	 * To receiving data from source.
	 *
	 * @param source Data source.
	 * @param size
	 */
	@Override
	public char[] receive(Reader source, int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");

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


	@Override
	public void run() {

	}
}
