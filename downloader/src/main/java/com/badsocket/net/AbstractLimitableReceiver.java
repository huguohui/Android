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
public abstract class AbstractLimitableReceiver implements LimitableReceiver {
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
	protected boolean completed = false;

	/**
	 * Received data length.
	 */
	protected long mReceivedLength;

	protected long sizeToReceive;

	/**
	 * Is stop receive?
	 */
	protected boolean isStop = false;

	protected boolean stoped;

	/**
	 * Size for next downloading.
	 */

	protected long lastReceivedLength = 0;

	public OnReceiveListener onReceiveListener;

	/**
	 * Construct a downloader by requester.
	 *
	 * @param is A {@link AbstractRequest}.
	 * @param writable
	 */
	public AbstractLimitableReceiver(InputStream is, OutputStream os) {
		mInputStream = is;
		outputStream = os;
	}

	protected void callOnReceiveListener(byte[] data) {
		if (onReceiveListener != null) {
			onReceiveListener.onReceive(this, data);
		}
	}

	protected byte[] receiveData(int bufferSize) throws IOException {
		byte[] buff = new byte[bufferSize];
		int read = 0;

		checkAvaliable(mInputStream);
		read = mInputStream.read(buff);
		mReceivedLength += read;
		buff = read == 0 ? null : Arrays.copyOf(buff, read);
		callOnReceiveListener(buff);
		return buff;
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

	@Override
	public void setLimit(long bytes) {

	}

	@Override
	public long getLimit() {
		return 0;
	}

	@Override
	public boolean limitSupported() {
		return false;
	}

	@Override
	public boolean isLimited() {
		return false;
	}

	@Override
	public long getAverageSpeed() {
		return 0;
	}

	protected void writeData(byte[] data) throws IOException {
		outputStream.write(data);
	}

	/**
	 * Receiving data with special length.
	 *
	 * @param size Special length.
	 */
	protected void receiveAndWrite(long size) throws IOException {
		int sizeToRec = 0;
		boolean fully = size < 0;
		while (!isStop) {
			if (fully) {
				sizeToRec = BUFFER_SIZE;
			}
			else {
				if (size == 0) {
					break;
				}
				sizeToRec = size <= BUFFER_SIZE ? (int) size : BUFFER_SIZE;
				size -= sizeToRec;
			}

			byte[] data = receiveData(sizeToRec);
			if (data == null) {
				break;
			}
		}
	}

	protected void finishReceive() throws IOException {
		completed = true;
		stoped = true;
	}

	/**
	 * Receiving data.
	 *
	 * @return Received data by byte.
	 * @throws IOException When I/O exception.
	 */
	@Override
	public void receive() throws IOException {
		receive(-1);
	}

	/**
	 * To downloading data from source, and save data to somewhere.
	 *
	 * @param size
	 */
	@Override
	public void receive(long size) throws IOException {
		try {
			receiveAndWrite(size);
		}
		catch (Exception e) {
			stoped = true;
			throw e;
		}
		finishReceive();
	}

	@Override
	public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
		this.onReceiveListener = onReceiveListener;
	}

	/**
	 * stop the object of managment.
	 */
	@Override
	public void stop() {
		isStop = true;
	}

	public InputStream getInputStream() {
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

	public boolean completed() {
		return completed;
	}

	public boolean stoped() {
		return stoped;
	}
}
