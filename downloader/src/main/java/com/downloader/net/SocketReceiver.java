package com.downloader.net;

import com.downloader.engine.Worker;
import com.downloader.io.FileWritable;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

/**
 * Socket receiver can receive data from input stream.
 */
public class SocketReceiver extends AbstractReceiver {
	/** Size for next started. */
	protected long mSizeWillReceive = 0;


	/**
	 * Construct a downloader by requester.
	 *
	 * @param is A {@link InputStream}.
	 */
	public SocketReceiver(InputStream is, FileWritable w, Worker worker) {
		super(is, w, worker);
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
		byte[] data = null;
		while(!isStop && null != (data = receiveBySize(BUFFER_SIZE))) {
			writeData(data);
		}

		finishReceive();
	}


	/**
	 * To started data from source, and save data to somewhere.
	 *
	 * @param size
	 */
	@Override
	public void receive(long size) throws IOException {
		receiveDataBySize(size);
		finishReceive();
	}


	protected void invokeListener() {
		if (!isStop && isFinished && onFinishedListener != null) {
			if (mSizeWillReceive < 0 || mSizeWillReceive == mReceivedLength) {
				onFinishedListener.onFinished(this);
			}
		}

		if (isStop && onStopListener != null) {
			onStopListener.onStop(this);
		}
	}


	protected void writeData(byte[] data) throws IOException {
		mWritable.write(mReceivedLength - data.length, data);
	}


	protected void finishReceive() throws IOException {
		isFinished = !isStop;
		invokeListener();
		mInputStream.close();
	}


	/**
	 * Receiving data with special length.
	 * @param size Special length.
	 */
	protected byte[] receiveBySize(int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");

		byte[] chunk = new byte[size];
		int count = 0, read = 0, freeLoop = 0;

		while(count < size) {
			int available = mInputStream.available();
			byte[] buff = new byte[BUFFER_SIZE];

			try {
				Thread.sleep(1);
			}
			catch ( Exception ex ) {
				ex.printStackTrace();
			}

			if (available == 0 && freeLoop++ != 50)
				continue;

			if (END_OF_STREAM == (read = mInputStream.read(buff, 0,
					count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE))) {
				isFinished = true;
				return null;
			}

			System.arraycopy(buff, 0, chunk, count, read);
			count += read;
			mReceivedLength += read;
			freeLoop = 0;
		}

		return chunk;
	}


	/**
	 * Receiving data with special length.
	 * @param size Special length.
	 */
	protected void receiveDataBySize(long size) throws IOException {
		int willRec = 0;
		while(!isStop && size > 0) {
			willRec = BUFFER_SIZE >= size ? (int) size : BUFFER_SIZE;
			writeData(receiveBySize(willRec));
			size -= willRec;
		}
	}


	/**
	 * Stop the object of managment.
	 */
	@Override
	public synchronized void stop() throws IOException {
		isStop = true;
	}


	/**
	 * To do some work.
	 */
	@Override
	public void work() throws Exception {
		receive();
	}
}
