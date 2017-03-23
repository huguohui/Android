package com.downloader.util;

import com.downloader.client.Workable;

import java.io.IOException;

/**
 * Interface of file writable.
 */
public interface FileWritable extends Writable {
	/**
	 * To writing data from special offset.
	 * @param offset Special offset.
	 * @param data Data will to writing.
	 */
	void write(long offset, byte[] data) throws IOException;


	/**
	 * To writing data with special length from special offset.
	 * @param offset Special offset.
	 * @param data Data will to writing.
	 * @param start Position of data start.
	 * @param end Position of data end.
	 */
	void write(long offset, byte[] data, int start, int end) throws IOException;
}
