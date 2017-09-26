package com.downloader.io;

import java.io.IOException;

/**
 * Interface of file writable.
 */
public interface ConcurrentFileWritable extends FileWritable {
	/**
	 * To writing data from special offset.
	 * @param offset Special offset.
	 * @param data Data will to writing.
	 */
	void write(long offset, long curOffset, byte[] data) throws IOException;
}
