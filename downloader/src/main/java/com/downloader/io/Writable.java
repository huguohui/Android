package com.downloader.io;

import java.io.IOException;

/**
 * Writable.
 */
public interface Writable {
	/**
	 * To writing data from offset 0.
	 * @param data Data will to writing.
	 */
	void write(byte[] data) throws IOException;


	/**
	 * To writing data with special length form offset 0.
	 * @param data Data will to writing.
	 * @param start Position of start.
	 * @param end Position of end.
	 */
	void write (byte[] data, int start, int end) throws IOException;
}
