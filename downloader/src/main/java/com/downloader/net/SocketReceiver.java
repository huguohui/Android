package com.downloader.net;

import com.downloader.engine.Stopable;
import com.downloader.engine.worker.Workable;

import java.io.IOException;

/**
 * SocketReceiver can receive some data form somewhere.
 *
 * @since 2015/11/29
 */
public interface SocketReceiver extends Stopable {
	/** Flag of stream end. */
	int END_OF_STREAM = -1;


	/**
	 * Receiving data.
	 * @return Received data by byte.
	 * @throws IOException When I/O exception.
	 * @throws java.net.ConnectException When connection exception.
	 */
	void receive() throws IOException;


	/**
	 * To downloading data from source, and save data to somewhere.
	 * @param len Length of data.
	 */
	void receive(long len) throws IOException;


	interface OnFinishedListener {
		void onFinished(SocketReceiver r);
	}


	interface OnStopListener {
		void onStop(SocketReceiver r);
	}


	interface OnReceiveListener {
		void onReceive(SocketReceiver r, byte[] data);
	}
}
