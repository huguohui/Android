package com.downloader.net;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Sender can send something to somewhere.
 */
public interface Send {
	/**
	 * Send data to somewhere.
	 *
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	void send() throws IOException;


	/**
	 * Send data to somewhere.
	 *
	 * @param data The data.
	 * @param to   Send to somewhere.
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	void send(byte[] data, OutputStream to) throws IOException;
}
