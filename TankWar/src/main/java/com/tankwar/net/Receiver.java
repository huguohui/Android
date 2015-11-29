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
	 * @throws IOException When I/O exception.
	 * @throws java.net.ConnectException When connection exception.
	 */
	void receive();


	/**
	 * To receiving data from source, and save data to somewhere.
	 * @param source Data source.
	 */
	void receive(InputStream source) throws IOException;


	/**
	 * To receiving data from source.
	 * @param source Data source.
	 */
	void receive(Reader source) throws IOException;
}
