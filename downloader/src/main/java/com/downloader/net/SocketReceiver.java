package com.downloader.net;

import com.downloader.engine.Worker;
import com.downloader.io.FileWritable;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.Arrays;

/**
 * Socket receiver can receive data from input stream.
 */
public class SocketReceiver extends AbstractReceiver {
	/** Size for next downloading. */
	protected long mSizeWillReceive = 0;


	/**
	 * Construct a downloader by requester.
	 *
	 * @param is A {@link InputStream}.
	 */
	public SocketReceiver(InputStream is, FileWritable w, Worker worker) {
		super(is, w, worker);
	}


	public SocketReceiver(InputStream is) {
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
	 * To downloading data from source, and save data to somewhere.
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
		mFileWriter.write(mReceivedLength - data.length, data);
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

		byte[] chunk = new byte[size], buff;
		int count = 0, read = 0, freeLoop = 0;

		while(count < size) {
			if (null == (buff = receiveFromStream(mInputStream,
					count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE))) {
				return null;
			}

			count += buff.length;
			System.arraycopy(buff, 0, chunk, count, read);
			mReceivedLength += read;
		}

		return chunk;
	}


	protected byte[] receiveFromStream(InputStream is, int size) throws IOException {
		int available = is.available(), read = 0;
		byte[] buff = new byte[size];

		try { Thread.sleep(1); } catch ( Exception ex ) {
			ex.printStackTrace();
		}

		if (available != 0) {
			if (END_OF_STREAM == (read = mInputStream.read(buff, 0, size))) {
				isFinished = true;
				return null;
			}

			buff = Arrays.copyOf(buff, read);
			onReceive(buff);
		}

		return buff;
	}


	protected void onReceive(byte[] data) {
		if (onReceiveListener == null) {
			return;
		}

		onReceiveListener.onReceive(data);
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
