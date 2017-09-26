package com.downloader.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class DataWriter implements DataWritable, Closeable {
	protected OutputStream mOutputStream;


	public DataWriter(OutputStream os) {
		mOutputStream = os;
	}


	@Override
	public void writeInt(int val) throws IOException {
		for (int i = 3; i >= 0; i--) {
			mOutputStream.write((byte) (((0xff << (i << 3)) & val) >> (i << 3)));
		}
	}


	@Override
	public void writeShort(short val) throws IOException {
		for (int i = 1; i >= 0; i--) {
			mOutputStream.write((byte) (((0xff << (i << 3)) & val) >> (i << 3)));
		}
	}


	@Override
	public void writeLong(long val) throws IOException {
		for (int i = 7; i > 0; i--) {
			mOutputStream.write((byte) (((0xff << (i << 3)) & val) >> (i << 3)));
		}
	}


	@Override
	public void writeByte(byte c) throws IOException {
		mOutputStream.write(c);
	}


	@Override
	public void writeByte(char c) throws IOException {
		writeShort((short) c);
	}


	/**
	 * To writing data from offset 0.
	 *
	 * @param data Data will to writing.
	 */
	@Override
	public void write(byte[] data) throws IOException {
		mOutputStream.write(data);
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
		mOutputStream.write(data, start, end);
	}


	@Override
	public void close() throws IOException {
		mOutputStream.close();
	}
}
