package com.tankwar.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Receiver can receive some data form somewhere.
 *
 * @since 2015/11/29
 */
public interface Receiver {
	/**
	 * Receiving data.
	 * @return Received data by byte.
	 * @throws IOException When I/O exception.
	 * @throws java.net.ConnectException When connection exception.
	 */
	byte receive() throws IOException;


	/**
	 * To receiving data from source, and save data to somewhere.
	 * @param source Data source.
	 */
	byte[] receive(InputStream source, int size) throws IOException;


	/**
	 * To receiving data from source.
	 * @param source Data source.
	 */
	char[] receive(Reader source, int size) throws IOException;
}
