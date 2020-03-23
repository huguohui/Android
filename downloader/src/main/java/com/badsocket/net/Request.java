package com.badsocket.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Sender can send something to somewhere.
 */
public interface Request extends Closeable {
	/**
	 * Open a request by speical internet downloadAddress.
	 *
	 * @param address Specials internet downloadAddress.
	 */
	void open(InetSocketAddress address) throws IOException;

	/**
	 * Open a request with a timeout by speical socket downloadAddress.
	 *
	 * @param address Specials socket downloadAddress.
	 * @param timeout Timeout in milliseconds.
	 */
	void open(InetSocketAddress address, int timeout) throws IOException;

	void open() throws IOException;

	void reopen() throws IOException;

	void reopen(InetSocketAddress address) throws IOException;

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

	/**
	 * Get response of this request.
	 */
	Response response() throws IOException;

	/**
	 * A range of data.
	 */
	class Range {
		/**
		 * Start offset.
		 */
		public long start;

		/**
		 * End offset.
		 */
		public long end;

		public Range(long s, long e) {
			if (e == 0 || s > e)
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
		Request build();
	}

	/**
	 * AbstractRequest state listener.
	 */
	public interface OnConnectedListener {
		void onConnected(AbstractRequest r);
	}

	public interface OnSendListener {
		void onSend(AbstractRequest r);
	}

	public interface OnResponseListener {
		void onResponse(Response r);
	}
}
