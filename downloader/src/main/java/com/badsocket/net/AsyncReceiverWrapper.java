package com.badsocket.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Created by skyrim on 2017/10/6.
 */

public class AsyncReceiverWrapper implements AsyncReceiver {

	private Receiver receiver;

	private long size;

	private ExecutorService executorService;

	private OnFinishedListener onFinishedListener;

	private OnStopListener onStopListener;

	public AsyncReceiverWrapper(ExecutorService executorService, Receiver rec) {
		receiver = rec;
		this.executorService = executorService;
	}

	public void receive() throws IOException {
		executorService.submit(new AsyncTask());
	}

	public void receive(long size) throws IOException {
		this.size = size;
		receive();
	}

	@Override
	public long getLastReceivedLength() {
		return receiver.getLastReceivedLength();
	}

	/**
	 * stop the object of managment.
	 */
	@Override
	public void stop() {
		receiver.stop();
	}

	@Override
	public boolean stoped() {
		return false;
	}

	public InputStream getInputStream() {
		return receiver.getInputStream();
	}

	public long getReceivedLength() {
		return receiver.getReceivedLength();
	}

	public boolean completed() {
		return receiver.completed();
	}

	@Override
	public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
		this.onFinishedListener = onFinishedListener;
	}

	@Override
	public void setOnStopListener(OnStopListener onStopListener) {
		this.onStopListener = onStopListener;
	}

	public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
		receiver.setOnReceiveListener(onReceiveListener);
	}

	private class AsyncTask implements Callable<Boolean> {
		public Boolean call() throws Exception {
			receiver.receive(size);
			if (receiver.completed()) {
				onFinishedListener.onFinished(receiver);
			}

			onStopListener.onStop(receiver);
			return receiver.completed();
		}
	}
}
