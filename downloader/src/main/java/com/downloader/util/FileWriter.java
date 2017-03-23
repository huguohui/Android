package com.downloader.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
		mWriter = new RandomAccessFile(file, "w");
		makeFileBySize(mFile, mLength);
	}


	/**
	 * Create a file by file.
	 * @param file File will to creating.
	 */
	public FileWriter(File file) throws IOException {
		this(file, 0);
	}


	/**
	 * To do some work.
	 */
	@Override
	public void work() throws Exception {
		synchronized (mQueue) {
			Map<Long, byte[]> map = mQueue.remove();
			if (map != null) {
				mWriter.seek(mOffset);
				for (Iterator<byte[]> it = map.values().iterator(); it.hasNext(); ) {
					mWriter.write(it.next());
				}
			}
		}
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

		synchronized (mQueue) {
			int writeLen = end - start;
			Map<Long, byte[]> map = new HashMap<>();
			data = writeLen == data.length ? data : Arrays.copyOfRange(data, start, end);
			map.put(offset, data);
			mQueue.add(map);
			mOffset = offset + writeLen;
		}
	}


	/**
	 * Make a file with special name and size.
	 *
	 * @param file File object.
	 * @param size File size.
	 */
	@Override
	public void makeFileBySize(File file, long size) throws IOException {
		if (file != null) {
			mWriter.setLength(size);
			step++;
		}
	}
}
