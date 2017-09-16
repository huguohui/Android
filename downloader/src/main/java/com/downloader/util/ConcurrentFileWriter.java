package com.downloader.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.downloader.base.AbstractReceiver.BUFFER_SIZE;

/**
 * File writer.
 */
public class ConcurrentFileWriter extends FileWriter {
	final public static int WRITE_BUFFER_SIZE = 1024 * 1024 * 1;

	private class DataBlock {
		private long offset;
		private ByteArrayOutputStream data;
		private long size;
		private int bufferSize;
	}

	protected Map<Long, DataBlock> dataBlocks = new ConcurrentHashMap<>();


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


	protected ByteArrayOutputStream createBuffer() {
		return new ByteArrayOutputStream(WRITE_BUFFER_SIZE);
	}


	protected DataBlock createDataBlock(Long curOff) {
		DataBlock dl = new DataBlock();
		dl.offset = curOff;
		dl.data = createBuffer();
		return dl;
	}


	public synchronized void write(long startOff, long curOff, byte[] data) throws IOException {
		if (data == null)
			throw new NullPointerException();

		if (!dataBlocks.containsKey(startOff))
			dataBlocks.put(startOff, createDataBlock(curOff));

		long surplus = 0; //多余的
		DataBlock dl = dataBlocks.get(startOff);
		dl.size += data.length;
		dl.bufferSize += data.length;

		if ((surplus = dl.bufferSize - WRITE_BUFFER_SIZE) >= 0) {
			Log.println(startOff);
			dl.data.write(data, 0, data.length - (int) surplus);
			write(dl);
			dl.offset += WRITE_BUFFER_SIZE;
			if (surplus != 0)
				dl.data.write(data, data.length - (int) surplus, data.length);

			return;
		}

		dl.data.write(data);
	}


	protected void write(DataBlock dl) throws IOException {
		super.write(dl.offset, dl.data.toByteArray());
		dl.bufferSize = 0;
		dl.data.reset();
	}


	protected void flush() throws IOException {
		Set<Map.Entry<Long, DataBlock>> es = dataBlocks.entrySet();
		for (Iterator<Map.Entry<Long, DataBlock>> it = es.iterator(); it.hasNext(); ) {
			Map.Entry<Long, DataBlock> map = it.next();
			DataBlock dl = map.getValue();
			if (dl.bufferSize != 0) {
				Log.println(dl.bufferSize);
				write(dl);
			}
		}
	}


	protected void release() {
		dataBlocks.clear();
		dataBlocks = null;
	}


	public synchronized void close() throws IOException {
		flush();
		super.close();
		release();
	}
}
