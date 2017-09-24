package com.downloader.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * File writer.
 */
public class FileWriter extends AbstractFileWriter {
	/** Step for writing. */
	protected int step = 0;

	/** Random access file for writer. */
	protected RandomAccessFile mWriter;


	/**
	 * Create a file with special name and size.
	 *
	 * @param file File will to creating.
	 * @param size Size of file will to creating.
	 */
	public FileWriter(File file, long size) throws IOException {
		super(file, size);
		makeFile(mFile, mLength);
	}


	/**
	 * Create a file by file.
	 * @param file File will to creating.
	 */
	public FileWriter(File file) throws IOException {
		this(file, 0);
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
	public void write(long offset, byte[] data, int start, int end) throws IOException {
		if (offset < 0) {
			throw new IllegalArgumentException("Specials offset is invalid!");
		}
		else if (data == null) {
			throw new NullPointerException("Data for writing can't be null!");
		}
		else if (start < 0 || end < 0 || end < start) {
			throw new IllegalArgumentException("Specials start or end is invalid!");
		}

		data = end - start == data.length ? data : Arrays.copyOfRange(data, start, end);
		mWriter.seek(offset);
		mWriter.write(data);
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
		if (file == null) {
			throw new FileNotFoundException();
		}

		mWriter = new RandomAccessFile(file, "rw");
		if (size > 0) {
			mWriter.setLength(size);
		}
	}


	@Override
	public void close() throws IOException {
		mWriter.close();
	}
}
