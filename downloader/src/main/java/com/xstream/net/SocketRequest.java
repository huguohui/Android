package com.xstream.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Sender can send something to somewhere.
 */
public interface SocketRequest extends Closeable {
	/**
	 * Open a request by speical internet address.
	 * @param address Specials internet address.
	 */
	void open(SocketAddress address) throws IOException;


	/**
	 * Open a request with a timeout by speical socket address.
	 * @param address Specials socket address.
	 * @param timeout Timeout in milliseconds.
	 */
	void open(SocketAddress address, int timeout) throws IOException;


	void open() throws IOException;


	void reopen() throws IOException;


	void reopen(SocketAddress address) throws IOException;


	/**
	 * Request data to somewhere.
	 *
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	void send() throws IOException;


	/**
	 * Request data to somewhere.
	 *
	 * @param data The data.
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	void send(byte[] data) throws IOException;


	Socket socket() throws IOException;


	boolean connected();


	boolean sent();


	boolean closed();


	/** Get response of this request. */
	SocketResponse response() throws IOException;


	/**
	 * A range of data.
	 */
	class Range {
		/** Start offset. */
		public long start;

		/** End offset. */
		public long end;

		public Range(long s, long e) {
			if (e > 0 && s > e || e == 0)
				throw new IllegalArgumentException("The end must >= start and end must != 0!");

			this.start = s;
			this.end = e;
		}

		public Range(long s) {
			this(s, -1);
		}


		public long getRange() {
			return end - start;
		}


		public String toString() {
			return String.format("range: %d-%d", start, end);
		}
	}


	interface RequestBuilder {
		SocketRequest build();
	}


	/**
	 * AbstractSocketRequest state listener.
	 */
	public interface OnConnectedListener {
		void onConnected(AbstractSocketRequest r);
	}


	public interface OnSendListener {
		void onSend(AbstractSocketRequest r);
	}


	public interface OnResponseListener {
		void onResponse(SocketResponse r);
	}
}
