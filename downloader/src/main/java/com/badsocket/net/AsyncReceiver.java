package com.badsocket.net;

import com.badsocket.io.writer.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * Created by skyrim on 2017/10/6.
 */

public class AsyncReceiver implements Receiver, Callable<Long> {

	protected AbstractReceiver receiver;

	protected long size;

	public AsyncReceiver(Receiver rec) {
		receiver = (AbstractReceiver) rec;
	}

	public void receive() throws IOException {
		receiver.receive();
	}

	public void receive(long size) {
		this.size = size;
	}

	@Override
	public long dataOffsetBegin() {
		return receiver.dataOffsetBegin();
	}

	@Override
	public long dataOffsetEnd() {
		return receiver.dataOffsetEnd();
	}

	@Override
	public long getReceivedLengthFromLast() {
		return receiver.getReceivedLengthFromLast();
	}

	/**
	 * stop the object of managment.
	 */
	@Override
	public void stop() {
		receiver.stop();
	}

	@Override
	public boolean isStoped() {
		return false;
	}

	public InputStream getInputStream() {
		return receiver.getInputStream();
	}

	public long getReceivedLength() {
		return receiver.getReceivedLength();
	}

	public boolean isFinished() {
		return receiver.isFinished();
	}

	public boolean isStop() {
		return receiver.isStop();
	}

	public Writer getFileWriter() {
		return receiver.getFileWriter();
	}

	public void setFileWriter(Writer fileWriter) {
		receiver.setFileWriter(fileWriter);
	}

	public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
		receiver.setOnFinishedListener(onFinishedListener);
	}

	public void setOnStopListener(OnStopListener onStopListener) {
		receiver.setOnStopListener(onStopListener);
	}

	public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
		receiver.setOnReceiveListener(onReceiveListener);
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public Long call() throws Exception {
		if (size != 0) {
			receiver.receive(size);
		}
		else {
			receiver.receive();
		}
		return receiver.getReceivedLength();
	}
}
