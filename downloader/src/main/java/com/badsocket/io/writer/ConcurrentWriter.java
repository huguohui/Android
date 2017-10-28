package com.badsocket.io.writer;

import java.io.IOException;

/**
 * Interface of file writable.
 */
public interface ConcurrentWriter extends Writer {
	/**
	 * To writing data from special offset.
	 * @param offset Special offset.
	 * @param data Data will to writing.
	 */
	void write(long offset, long curOffset, byte[] data) throws IOException;
}
