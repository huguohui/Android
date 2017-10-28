package com.xstream.io.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DataWriter extends AbstractWriter implements DataWritable {

	protected OutputStream mOutputStream;

	protected File file;


	public DataWriter(OutputStream os) {
		mOutputStream = os;
	}


	public DataWriter(File file) throws IOException {
		mOutputStream = new FileOutputStream(file);
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
		for (int i = 1; i >= 0; i--) {
			writeInt((int) (val >>> i * Integer.SIZE));
		}
	}


	@Override
	public void writeByte(byte c) throws IOException {
		mOutputStream.write(c);
	}


	@Override
	public void writeChar(char c) throws IOException {
		writeShort((short) c);
	}


	@Override
	public void writeChars(char[] cs) throws IOException {
		writeChars(cs, 0, cs.length);
	}


	@Override
	public void writeChars(char[] cs, int s, int e) throws IOException {
		for (int i = s; i < e; i++) {
			writeChar(cs[i]);
		}
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


	@Override
	public void close() throws IOException {
		mOutputStream.close();
	}


	/**
	 * Make a file with special name and size.
	 *
	 * @param file File object.
	 * @param size File size.
	 */
	@Override
	public void makeFile(File file, long size) throws IOException {

	}


	/**
	 * Flushes this stream by writing any buffered output to the underlying
	 * stream.
	 *
	 * @throws IOException If an I/O error occurs
	 */
	@Override
	public void flush() throws IOException {

	}
}
