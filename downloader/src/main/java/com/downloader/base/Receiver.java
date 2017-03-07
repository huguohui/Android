package com.downloader.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Receiver can receive some data form somewhere.
 *
 * @since 2015/11/29
 */
public interface Receiver {
	/** Flag of stream end. */
	int END_OF_STREAM = -1;
	
	
	/**
	 * Receiving data.
	 * @return Received data by byte.
	 * @throws IOException When I/O exception.
	 * @throws java.net.ConnectException When connection exception.
	 */
	void receive() throws IOException;


	/**
	 * To receiving data from source, and save data to somewhere.
	 * @param len Length of data.
	 */
	void receive(int len) throws IOException;
}