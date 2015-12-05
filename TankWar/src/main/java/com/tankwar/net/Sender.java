package com.tankwar.net;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Sender can send something to somewhere.
 */
public interface Sender {
	/**
	 * Send data to somewhere.
	 *
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	boolean send() throws IOException;


	/**
	 * Send data to somewhere.
	 *
	 * @param data The data.
	 * @param to   Send to somewhere.
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	boolean send(byte[] data, OutputStream to) throws IOException;
}
