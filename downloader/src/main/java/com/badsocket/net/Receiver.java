package com.badsocket.net;

import com.badsocket.core.Stopable;

import java.io.IOException;
import java.io.InputStream;

/**
 * Receiver can receive some data form somewhere.
 *
 * @since 2015/11/29
 */
public interface Receiver extends Stopable {
	/** Flag of stream end. */
	int END_OF_STREAM = -1;

	void receive() throws IOException;

	void receive(long len) throws IOException;

	long getLastReceivedLength();

	long getReceivedLength();

	long getAverageSpeed();

	boolean completed();

	InputStream getInputStream();

	void setOnReceiveListener(OnReceiveListener onReceiveListener);

	interface OnReceiveListener {
		void onReceive(Receiver r, byte[] data);
	}

}
