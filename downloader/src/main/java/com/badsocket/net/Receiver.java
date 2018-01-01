package com.badsocket.net;

import com.badsocket.core.Stopable;

import java.io.IOException;

/**
 * Receiver can receive some data form somewhere.
 *
 * @since 2015/11/29
 */
public interface Receiver extends Stopable {
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


	long getReceivedLength();


	interface OnFinishedListener {
		void onFinished(Receiver r);
	}


	interface OnStopListener {
		void onStop(Receiver r);
	}


	interface OnReceiveListener {
		void onReceive(Receiver r, byte[] data);
	}
}
