package com.downloader.util;

import com.downloader.client.Workable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * File writer.
 */
public abstract class AbstractFileWriter implements FileWritable, Workable {
	/** The file for writing. */
	protected File mFile;

	/** The data queue for writing. */
	protected Queue<Map<Long, byte[]>> mQueue = new ConcurrentLinkedQueue<>();

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
}
