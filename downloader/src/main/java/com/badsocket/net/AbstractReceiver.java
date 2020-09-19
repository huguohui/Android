package com.badsocket.net;

import com.badsocket.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * Download some data form a place.
 */
public abstract class AbstractReceiver implements Receiver {
	/**
	 * Default buffer size.
	 */
	public final static int BUFFER_SIZE = (1024 << 3) * 10;

	/**
	 * The requester object.
	 */
	protected InputStream mInputStream;

	protected OutputStream outputStream;

	/**
	 * Writable of receiver.
	 */
	protected FileChannel fileChannel;

	/**
	 * Is finished?
	 */
	protected boolean isFinished = false;

	/**
	 * Received data length.
	 */
	protected long mReceivedLength;

	/**
	 * Is stop receive?
	 */
	protected boolean isStop = false;

	/**
	 * Size for next downloading.
	 */

	protected long lastReceivedLength = 0;

	protected long dataOffsetBegin;

	protected long dataOffsetEnd;

	protected OnFinishedListener onFinishedListener;

	protected OnStopListener onStopListener;

	protected OnReceiveListener onReceiveListener;

	/**
	 * Construct a downloader by requester.
	 *
	 * @param is A {@link AbstractRequest}.
	 * @param writable
	 */
	public AbstractReceiver(InputStream is, OutputStream os) {
		mInputStream = is;
		outputStream = os;
	}

	protected AbstractReceiver() {
	}

	/**
	 * Receiving data with special length.
	 *
	 * @param size Special length.
	 */
	protected byte[] receiveData(int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");

		byte[] chunk = new byte[size], buff = new byte[0];
		int count = 0, read = 0;
		while (!isStop && count < size) {
			checkAvaliable(mInputStream);
			if (null == (buff = receiveFromStream(mInputStream,
					count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE))) {
				break;
			}

			read = buff.length;
			System.arraycopy(buff, 0, chunk, count, read);
			count += read;
			mReceivedLength += read;
		}

		return buff == null || read == 0 ? null : Arrays.copyOf(chunk, count);
	}

	protected void checkAvaliable(InputStream is) throws IOException {
		int idle = 1, maxWaitMs = 1;
		try {
			Thread.sleep(is.available() != 0 ? idle : maxWaitMs);
		}
		catch (InterruptedException ex) {
			Log.d("从Sleep状态中断！");
		}
	}

	protected byte[] receiveFromStream(InputStream is, int size) throws IOException {
		int read = 0;
		byte[] buff = new byte[size];
		if (END_OF_STREAM == (read = mInputStream.read(buff, 0, size))) {
			return null;
		}

		buff = Arrays.copyOf(buff, read);
		return buff;
	}

	/**
	 * Receiving data with special length.
	 *
	 * @param size Special length.
	 */
	protected void receiveAndWrite(int size) throws IOException {
		int willRec = 0;
		boolean fully = size < 0;
		while (!isStop) {
			if (fully) {
				willRec = BUFFER_SIZE;
			}
			else {
				if (size <= 0) {
					break;
				}
				willRec = Math.min(BUFFER_SIZE, size);
				size -= willRec;
			}

			byte[] data = receiveData(willRec);
			if (data == null) {
				break;
			}
			outputStream.write(data);
		}
	}

	protected void invokeListener() {
		if (!isStop && isFinished && onFinishedListener != null) {
			onFinishedListener.onFinished(this);
		}

		if (isStop && onStopListener != null) {
			onStopListener.onStop(this);
		}
	}

	protected void finishReceive() throws IOException {
		isFinished = !isStop;
		invokeListener();
	}

	/**
	 * Receiving data.
	 *
	 * @return Received data by byte.
	 * @throws IOException When I/O exception.
	 */
	@Override
	public void receive() throws IOException {
		byte[] data = null;
		receiveAndWrite(-1);
		finishReceive();
	}

	/**
	 * To downloading data from source, and save data to somewhere.
	 *
	 * @param size
	 */
	@Override
	public void receive(int size) throws IOException {
		receiveAndWrite(size);
		finishReceive();
	}

	@Override
	public long dataOffsetBegin() {
		return dataOffsetBegin;
	}

	@Override
	public long dataOffsetEnd() {
		return dataOffsetEnd;
	}

	protected void onReceive(byte[] data) {
		if (onReceiveListener == null) {
			return;
		}

		onReceiveListener.onReceive(this, data);
	}

	/**
	 * stop the object of managment.
	 */
	@Override
	public void stop() {
		isStop = true;
	}

	public InputStream inputStream() {
		return mInputStream;
	}

	public long getReceivedLength() {
		return mReceivedLength;
	}

	public long getLastReceivedLength() {
		long current = mReceivedLength - lastReceivedLength;
		lastReceivedLength = mReceivedLength;
		return current;
	}

	public boolean finished() {
		return isFinished;
	}

	public boolean stoped() {
		return isStop;
	}

	public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
		this.onFinishedListener = onFinishedListener;
	}

	public void setOnStopListener(OnStopListener onStopListener) {
		this.onStopListener = onStopListener;
	}

	public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
		this.onReceiveListener = onReceiveListener;
	}
}
