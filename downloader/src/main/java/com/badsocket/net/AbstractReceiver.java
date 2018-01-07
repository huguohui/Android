package com.badsocket.net;

import com.badsocket.io.writer.Writer;
import com.badsocket.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


/**
 * Download some data form a place.
 */
public abstract class AbstractReceiver implements Receiver {
	/** Default buffer size. */
	public final static int BUFFER_SIZE = 1024 << 3;

	/** The requester object. */
	protected InputStream mInputStream;

	/** Writable of receiver. */
	protected Writer mFileWriter;

	/** Is finished? */
	protected boolean isFinished = false;

	/** Received data length. */
	protected volatile long mReceivedLength;

	/** Is stop receive? */
	protected boolean isStop = false;

	/** Size for next downloading. */
	protected long mSizeWillReceive = 0;

	protected long lastReceivedLength = 0;

	protected long dataOffsetBegin;

	protected long dataOffsetEnd;

	protected OnFinishedListener onFinishedListener;

	protected OnStopListener onStopListener;

	protected OnReceiveListener onReceiveListener;


	/**
	 * Construct a downloader by requester.
	 * @param is A {@link AbstractRequest}.
	 */
	public AbstractReceiver(InputStream is, Writer writable) {
		mInputStream = is;
		mFileWriter = writable;
	}


	protected AbstractReceiver() {
	}


	/**
	 * Receiving data with special length.
	 * @param size Special length.
	 */
	protected byte[] receiveData(int size) throws IOException {
		if (size <= 0)
			throw new IllegalArgumentException("The size is illegal!");

		byte[] chunk = new byte[size], buff;
		int count = 0, read = 0, freeLoop = 0;

		while(!isStop && count < size) {
			checkAvaliable(mInputStream);
			if (null == (buff = receiveFromStream(mInputStream,
					count + BUFFER_SIZE > size ? size - count : BUFFER_SIZE))) {
				return null;
			}

			read = buff.length;
			System.arraycopy(buff, 0, chunk, count, read);
			count += read;
			mReceivedLength += read;
		}

		return chunk;
	}


	protected void checkAvaliable(InputStream is) throws IOException {
		int idle = 1, maxWaitMs = 1;
		try { Thread.sleep(is.available() != 0 ? idle : maxWaitMs); } catch ( InterruptedException ex ) {
			Log.debug("从Sleep状态中断！");
		}
	}


	protected byte[] receiveFromStream(InputStream is, int size) throws IOException {
		int read = 0;
		byte[] buff = new byte[size];
		if (END_OF_STREAM == (read = mInputStream.read(buff, 0, size))) {
			return null;
		}

		buff = Arrays.copyOf(buff, read);
		onReceive(buff);

		return buff;
	}


	/**
	 * Receiving data with special length.
	 * @param size Special length.
	 */
	protected void receiveAndWrite(long size) throws IOException {
		int willRec = 0;
		while(!isStop && size > 0) {
			willRec = BUFFER_SIZE >= size ? (int) size : BUFFER_SIZE;
			writeData(receiveData(willRec));
			size -= willRec;
		}
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
		flushWriter();
		Log.debug("Received length " + mReceivedLength + " of data.");
	}


	protected void flushWriter() throws IOException {

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
		Log.debug("Start receive data...");
		byte[] data = null;
		while(null != (data = receiveData(BUFFER_SIZE))) {
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


	public InputStream getInputStream() {
		return mInputStream;
	}


	public long getReceivedLength() {
		return mReceivedLength;
	}


	public long getReceivedLengthFromLast() {
		long current = mReceivedLength - lastReceivedLength;
		lastReceivedLength = mReceivedLength;
		return current;
	}


	public boolean isFinished() {
		return isFinished;
	}


	public boolean isStop() {
		return isStop;
	}


	public Writer getFileWriter() {
		return mFileWriter;
	}


	public AbstractReceiver setFileWriter(Writer fileWriter) {
		mFileWriter = fileWriter;
		return this;
	}


	public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
		this.onFinishedListener = onFinishedListener;
	}


	public void setOnStopListener(OnStopListener onStopListener) {
		this.onStopListener = onStopListener;
	}


	public AbstractReceiver setOnReceiveListener(OnReceiveListener onReceiveListener) {
		this.onReceiveListener = onReceiveListener;
		return this;
	}
}
