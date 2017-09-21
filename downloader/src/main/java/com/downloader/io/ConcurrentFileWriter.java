package com.downloader.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File writer.
 */
public class ConcurrentFileWriter extends FileWriter implements ConcurrentDataWritable {
	final public static int WRITE_BUFFER_SIZE = 1024 * 1024 * 1;

	private class DataBuffer {
		private long offset;
		private long size;
		private int bufferSize;
		private byte[] surplusData;
		private boolean isFull;
		private ByteArrayOutputStream buffer;

		private DataBuffer(long offset, ByteArrayOutputStream bos) {
			this.offset = offset;
			buffer = bos;
		}


		private DataBuffer save(byte[] data) throws IOException {
			int surplus = 0;
			if (surplusData != null) {
				buffer.write(surplusData);
				bufferSize = surplusData.length;
				surplusData = null;
			}

			if (data != null) {
				size += data.length;
				bufferSize += data.length;
				isFull = (surplus = (bufferSize - WRITE_BUFFER_SIZE)) >= 0;
				buffer.write(data, 0, surplus != 0 ? data.length - surplus : data.length);
				surplusData = isFull ? Arrays.copyOfRange(data, surplus, data.length) : null;
			}

			return this;
		}


		private boolean isFull() {
			return isFull;
		}


		private void clean() throws IOException {
			bufferSize = 0;
			buffer.reset();
		}
	}

	protected Map<Long, DataBuffer> dataBuffers = new ConcurrentHashMap<>();


	/**
	 * Create a file with special name and size.
	 *
	 * @param file File will to creating.
	 * @param size Size of file will to creating.
	 */
	public ConcurrentFileWriter(File file, long size) throws IOException {
		super(file, size);
	}


	public ConcurrentFileWriter(File file) throws IOException {
		super(file);
	}


	protected DataBuffer createDataBuffer(Long off) {
		return new DataBuffer(off, new ByteArrayOutputStream(WRITE_BUFFER_SIZE));
	}


	public synchronized void write(long startOff, long curOff, byte[] data) throws IOException {
		if (data == null)
			throw new NullPointerException();

		if (!dataBuffers.containsKey(startOff))
			dataBuffers.put(startOff, createDataBuffer(startOff));

		DataBuffer buffer = dataBuffers.get(startOff);
		if (buffer.save(data).isFull()) {
			writeToFile(buffer);
		}
	}


	public synchronized void write(long offset, byte[] data, int s, int e) throws IOException {

	}


	protected void writeToFile(DataBuffer dl) throws IOException {
		super.write(dl.offset, dl.buffer.toByteArray());
		dl.clean();
	}


	protected void flush() throws IOException {
		Set<Map.Entry<Long, DataBuffer>> es = dataBuffers.entrySet();
		for (Iterator<Map.Entry<Long, DataBuffer>> it = es.iterator(); it.hasNext(); ) {
			Map.Entry<Long, DataBuffer> map = it.next();
			DataBuffer dl = map.getValue();
			if (dl.surplusData != null)
				dl.save(null);

			if (dl.bufferSize != 0)
				writeToFile(dl);
		}
	}


	protected void release() {
		dataBuffers.clear();
		dataBuffers = null;
	}


	public synchronized void close() throws IOException {
		flush();
		super.close();
		release();
	}
}
