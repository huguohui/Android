package com.badsocket.io.writer;

import java.io.IOException;

/**
 * File writer.
 */
public abstract class AbstractWriter implements Writer {

	/**
	 * Offset of file writer.
	 */
	protected long mOffset = 0;

	/**
	 * To writing data from offset 0.
	 *
	 * @param data Data will to writing.
	 */
	@Override
	public void write(byte[] data) throws IOException {
		write(mOffset, data);
	}

	/**
	 * To writing data with special length form offset 0.
	 *
	 * @param data  Data will to writing.
	 * @param start Position of start.
	 * @param end   Position of end.
	 */
	@Override
	public void write(byte[] data, int start, int end) throws IOException {
		write(mOffset, data, start, end);
	}

	/**
	 * To writing data from special offset.
	 *
	 * @param offset Special offset.
	 * @param data   Data will to writing.
	 */
	@Override
	public void write(long offset, byte[] data) throws IOException {
		write(offset, data, 0, data.length);
	}

	/**
	 * To writing data with special length from special offset.
	 *
	 * @param offset Special offset.
	 * @param data   Data will to writing.
	 * @param start  Position of data start.
	 * @param end    Position of data end.
	 */
	@Override
	public abstract void write(long offset, byte[] data, int start, int end) throws IOException;
}
