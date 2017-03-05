package com.downloader.base;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Sender can send something to somewhere.
 */
public interface Request {
	/**
	 * Open a request by speical internet address.
	 * @param address Specials internet address.
	 */
	void open(SocketAddress address) throws Exception;


	/**
	 * Open a request with a timeout by speical socket address.
	 * @param address Specials socket address.
	 * @param timeout Timeout in milliseconds.
	 */
	void open(SocketAddress address, int timeout) throws Exception;


	/**
	 * Request data to somewhere.
	 *
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	void send() throws Exception;


	/**
	 * Request data to somewhere.
	 *
	 * @param data The data.
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	void send(byte[] data) throws Exception;


	/**
	 * Closes this request.
	 */
	void close() throws Exception;
}
