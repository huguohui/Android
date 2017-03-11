package com.downloader.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;

/**
 * File writer.
 */
public class FileWriter implements FileWritable {
	/** The file for writing. */
	protected File mFile;

	/** The data queue for writing. */
	protected Queue<Map<Long, byte[]> mQueue = new ArrayDeque<>();

	/** Offset of file writer. */
	protected long mOffset = 0;


	/**
	 * Constructor a object for file writer.
	 */
	public FileWrite(File file, ) throws IOException {
		if (file == null)
			throw new NullPointerException();

		mFile = file;
	}


	/**
	 * To writing data from offset 0.
	 *
	 * @param data Data will to writing.
	 */
	@Override
	public void write(byte[] data) throws IOException {
		write(offset, data);
	}


	/**
	 * To writing data with special length form offset 0.
	 *
	 * @param data  Data will to writing.
	 * @param start Position of start.
	 * @param end   Position of end.
	 */
	@Override
	public void wirte(byte[] data, int start, int end) throws IOException {
		write(offset, data, start, end);
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
	public void write(long offset, byte[] data, int start, int end) throws IOException {
		if (data == null)
			throw new NullPointerException();

		if (offset < 0)
			throw new IllegalArgumentsException("Special offset is illegal!");

		Map<int, byte[]> map = new HashMap<>();
		data = Arrays.copyRangeOf(data, start, end);
		map.put(offset, data);
		mQueue.add(map);
		mOffset = offset + (end - start);
	}
}
