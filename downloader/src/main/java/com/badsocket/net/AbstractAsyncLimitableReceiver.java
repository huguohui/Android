package com.badsocket.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public abstract class AbstractAsyncLimitableReceiver
		extends AbstractLimitableReceiver
		implements AsyncReceiver {

	protected OnFinishedListener onFinishedListener;

	protected OnStopListener onStopListener;

	protected ExecutorService executorService;

	protected long sizeToReceive;

	public AbstractAsyncLimitableReceiver(ExecutorService executorService, InputStream is, OutputStream os) {
		super(is, os);
		this.executorService = executorService;
	}

	protected void invokeOnReceiveListener(byte[] data) {
		onReceiveListener.onReceive(this, data);
	}

	@Override
	protected void writeData(byte[] data) throws IOException {
		super.writeData(data);
	}

	@Override
	public void receive() throws IOException {
		executorService.submit(new AsyncTask(this));
	}

	@Override
	public void receive(long size) throws IOException {
		sizeToReceive = size;
	}

	@Override
	public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
		this.onFinishedListener = onFinishedListener;
	}

	@Override
	public void setOnStopListener(OnStopListener onStopListener) {
		this.onStopListener = onStopListener;
	}

	private class AsyncTask implements Callable<Boolean> {
		AbstractAsyncLimitableReceiver asyncSocketReceiver;

		AsyncTask(AbstractAsyncLimitableReceiver asyncSocketReceiver) {
			this.asyncSocketReceiver = asyncSocketReceiver;
		}

		public Boolean call() throws Exception {
			asyncSocketReceiver.receive(asyncSocketReceiver.sizeToReceive);
			if (asyncSocketReceiver.completed()) {
				onFinishedListener.onFinished(asyncSocketReceiver);
			}
			onStopListener.onStop(asyncSocketReceiver);
			return asyncSocketReceiver.completed();
		}
	}
}
