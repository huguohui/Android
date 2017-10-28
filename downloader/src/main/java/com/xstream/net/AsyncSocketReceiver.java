package com.xstream.net;

import com.xstream.engine.worker.Workable;
import com.xstream.engine.worker.Worker;
import com.xstream.io.writer.Writer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by skyrim on 2017/10/6.
 */

public class AsyncSocketReceiver extends AbstractSocketReceiver implements Workable {

	protected AbstractSocketReceiver receiver;

	protected Worker worker;

	protected long size;


	public AsyncSocketReceiver(SocketReceiver rec, Worker w) {
		receiver = (AbstractSocketReceiver) rec;
		worker = w;
	}


	public void receive() throws IOException {
	//	receiver.receive();
		worker.add(this);
	}


	public void receive(long size) {
		this.size = size;
		worker.add(this);
	}



	/**
	 * To do some work.
	 */
	@Override
	public void work() throws Exception {
		if (size != 0) {
			receiver.receive(size);
		}
		else {
			receiver.receive();
		}
	}


	/**
	 * stop the object of managment.
	 */
	@Override
	public synchronized void stop() {
		receiver.stop();
	}


	public InputStream getInputStream() {
		return receiver.getInputStream();
	}


	public synchronized long getReceivedLength() {
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


	public AbstractSocketReceiver setFileWriter(Writer fileWriter) {
		receiver.setFileWriter(fileWriter);
		return this;
	}


	public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
		receiver.setOnFinishedListener(onFinishedListener);
	}


	public void setOnStopListener(OnStopListener onStopListener) {
		receiver.setOnStopListener(onStopListener);
	}


	public AbstractSocketReceiver setOnReceiveListener(OnReceiveListener onReceiveListener) {
		receiver.setOnReceiveListener(onReceiveListener);
		return this;
	}
}
