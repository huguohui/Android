package com.downloader.io.writer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * File writer.
 */
public class SimpleFileWriter extends AbstractWriter implements FileWriter {
	/** Step for writing. */
	protected int step = 0;

	/** Random access file for writer. */
	protected RandomAccessFile mWriter;

	protected File mFile;

	protected long mLength;


	/**
	 * Create a file with special name and size.
	 *
	 * @param file File will to creating.
	 * @param size Size of file will to creating.
	 */
	public SimpleFileWriter(File file, long size) throws IOException {
		mFile = file;
		mLength = size;
		makeFile(mFile, mLength);
	}


	/**
	 * Create a file by file.
	 * @param file File will to creating.
	 */
	public SimpleFileWriter(File file) throws IOException {
		this(file, 0);
	}


	protected void ensureWritable() {
		if (mWriter == null)
			throw new NullPointerException();
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
	public synchronized void write(long offset, byte[] data, int start, int end) throws IOException {
		ensureWritable();
		if (offset < 0) {
			throw new IllegalArgumentException("Specials offset is invalid!");
		}
		else if (data == null) {
			throw new NullPointerException("Data for writing can't be null!");
		}
		else if (start < 0 || end < 0 || end < start) {
			throw new IllegalArgumentException("Specials start or end is invalid!");
		}

		writeData(offset, data, start, end);
	}


	protected void writeData(long offset, byte[] data, int start, int end) throws IOException {
		mWriter.seek(offset);
		mWriter.write(data, start, end);
		mOffset = offset + data.length;
		mLength += data.length;
	}


	/**
	 * Make a file with special name and size.
	 *
	 * @param file File object.
	 * @param size File size.
	 */
	@Override
	public void makeFile(File file, long size) throws IOException {
		mWriter = new RandomAccessFile(file, "rw");
		if (size > 0) {
			mWriter.setLength(size);
		}
	}


	@Override
	public void flush() throws IOException {

	}


	@Override
	public void close() throws IOException {
		flush();
		mWriter.close();
	}
}
