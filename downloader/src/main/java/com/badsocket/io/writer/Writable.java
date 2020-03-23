package com.badsocket.io.writer;

import java.io.File;
import java.io.IOException;

/**
 * Writable.
 */
public interface Writable {
	/**
	 * To writing data from offset 0.
	 *
	 * @param data Data will to writing.
	 */
	void write(byte[] data) throws IOException;

	/**
	 * To writing data with special length form offset 0.
	 *
	 * @param data  Data will to writing.
	 * @param start Position of start.
	 * @param end   Position of end.
	 */
	void write(byte[] data, int start, int end) throws IOException;

	/**
	 * To writing data from special offset.
	 *
	 * @param offset Special offset.
	 * @param data   Data will to writing.
	 */
	void write(long offset, byte[] data) throws IOException;

	/**
	 * To writing data with special length from special offset.
	 *
	 * @param offset Special offset.
	 * @param data   Data will to writing.
	 * @param start  Position of data start.
	 * @param end    Position of data end.
	 */
	void write(long offset, byte[] data, int start, int end) throws IOException;

	/**
	 * Make a file with special name and size.
	 *
	 * @param file File object.
	 * @param size File size.
	 */
	void makeFile(File file, long size) throws IOException;
}
