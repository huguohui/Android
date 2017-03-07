package com.downloader.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * File writer.
 */
public class FileWriter implements FileWritable {
	/** The file for writing. */
	protected File mFile;

	/** The data queue for writing. */
	protected Queue<byte[]> mQueue = new ArrayDeque<>();


	/**
	 * To writing data from offset 0.
	 *
	 * @param data Data will to writing.
	 */
	@Override
	public   void write(byte[] data) throws IOException {

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

	}


	/**
	 * To writing data from special offset.
	 *
	 * @param offset Special offset.
	 * @param data   Data will to writing.
	 */
	@Override
	public void write(long offset, byte[] data) throws IOException {

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

	}
}
