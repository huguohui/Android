package com.downloader.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * File writer.
 */
public abstract class AbstractFileWriter implements FileWritable, Closeable {
	/** The file for writing. */
	protected File mFile;

	/** Offset of file writer. */
	protected long mOffset = 0;

	/** Size of file. */
	protected long mLength = 0;


	/**
	 * Create a file with special name and size.
	 * @param file File will to creating.
	 * @param size Size of file will to creating.
	 */
	public AbstractFileWriter(File file, long size) throws IOException {
		if (file == null)
			throw new NullPointerException();

		mFile = file;
		mLength = Math.max(0, size);
		makeFile(file, mLength);
	}


	/**
	 * Create a file by file.
	 * @param file File will to creating.
	 */
	public AbstractFileWriter(File file) throws IOException {
		this(file, 0L);
	}


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


	/**
	 * Make a file with special name and size.
	 * @param file File object.
	 * @param size File size.
	 */
	public abstract void makeFile(File file, long size) throws IOException;


	public abstract void flush() throws IOException;
}
