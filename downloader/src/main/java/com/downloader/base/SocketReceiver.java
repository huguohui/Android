package com.downloader.base;

import com.downloader.util.Writable;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.Arrays;

/**
 * Socket receiver can receive data from input stream.
 */
public class SocketReceiver extends AbstractReceiver {
	/** Size for next receiving. */
	protected long mSizeWillReceive = 0;


	/**
	 * Construct a downloader by requester.
	 *
	 * @param is A {@link InputStream}.
	 */
	public SocketReceiver(InputStream is, Writable w) {
		this(is, w, null);
	}


	/**
	 * Construct a downloader by requester.
	 * @param is A {@link AbstractRequest}.
	 * @param r Range of data will to receiving.
	 */
	public SocketReceiver(InputStream is, Writable w, Range r) {
		super(is, w, r);

		if (isPortal)
			mSizeWillReceive = r.getRange();
	}


	public SocketReceiver() {
	}


	/**
	 * Receiving data.
	 *
	 * @return Received data by byte.
	 * @throws IOException      When I/O exception.
	 * @throws ConnectException When connection exception.
	 */
	@Override
	public void receive() throws IOException {
		byte[] data;
		while(null != (data = receiveData(BUFFER_SIZE)))
			mWritable.write(data);
	}


	/**
	 * To receiving data from source, and save data to somewhere.
	 *
	 * @param size
	 */
	@Override
	public void receive(long size) throws IOException {
		int willRec = 0;
		while(size > 0) {
			willRec = BUFFER_SIZE >= size ? (int) size : BUFFER_SIZE;
			mWritable.write(receiveData(willRec));
			size -= willRec;
		}
	}


	/**
	 * Receiving data with special length.
	 * @param size Special length.
	 */
	private byte[] receiveData(int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");

		byte[] chunk = new byte[size];
		int count = 0, read = 0, freeLoop = 0;

		while(count < size) {
			int available = mInputStream.available();
			byte[] buff = new byte[BUFFER_SIZE];

			if (available == 0 && freeLoop++ < 100) {
				try {
					Thread.sleep(1);
				}
				catch ( Exception ex ) {
					ex.printStackTrace();
				}

				continue;
			}

			if (END_OF_STREAM == (read = mInputStream.read(buff, 0,
					count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE))) {
				if (count != 0 && count != size) {
					mReceivedLength += read;
					return Arrays.copyOfRange(buff, 0, count);
				}

				return null;
			}

			System.arraycopy(buff, 0, chunk, count, read);
			count += read;
			freeLoop = 0;
			mReceivedLength += read;
		}

		return chunk;
	}


	@Override
	public void run() {
		try {
			if (isPortal)
				receive();
			else
				receive(mSizeWillReceive);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
