package com.downloader.base;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Socket request.
 */
public class SocketRequest extends AbstractRequest {
	/**
	 * Constructor a request by socket address.
	 * @param  socketAddress The requests address.
	 */
	public SocketRequest(SocketAddress socketAddress) throws IOException {
		setSocketAddress(socketAddress);
		open(socketAddress, mTimeout);
	}


	/**
	 * Empty constructor.
	 */
	public SocketRequest() {
	}


	/**
	 * Open a connection with timeout and prepare to send request.
	 * @param address The {@link SocketAddress} to describing a address.
	 * @param timeout The timeout time of connection.
	 */
	public void open(SocketAddress address, int timeout)
			throws IOException {
		if (!mState.equals(State.closed) && !mState.equals(State.ready)) {
			throw new ConnectException("Only open connection after request " +
					"closed or requester don't connected!");
		}

		mStartTime = System.currentTimeMillis();
		mSocket = new Socket();
		mState = State.connecting;
		mSocket.connect(address, timeout);
		mState = State.connected;
		isConnect = true;
	}


	/**
	 * Close connection.
	 */
	public void close() throws IOException {
		mState = State.closed;
		if (mSocket.isClosed() || !mSocket.isConnected())
			throw new ConnectException("The socket don't connected!");

		mSocket.shutdownInput();
		mSocket.shutdownOutput();
		mSocket.close();
		isConnect = false;
	}


	/**
	 * Get response of this request.
	 */
	@Override
	public Response response() throws Exception {
		return null;
	}


	/**
	 * Request data to somewhere.
	 *
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	@Override
	public void send() throws Exception {

	}


	/**
	 * Request data to somewhere.
	 *
	 * @param data The data.
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	@Override
	public void send(byte[] data) throws Exception {

	}


	/**
	 * Reopen the request with last data.
	 */
	@Override
	public void reopen() throws IOException {

	}
}
