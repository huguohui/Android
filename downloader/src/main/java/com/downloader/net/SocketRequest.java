package com.downloader.net;

import com.downloader.util.TimeUtil;

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
	 * Open a connection and prepare to send request.
	 *
	 * @return If connect success, return true else false.
	 */
	public void open(SocketAddress address) throws IOException {
		open(address, mTimeout);
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
		mConnectionTime = TimeUtil.getMillisTime() - mStartTime;
		mOutputStream = mSocket.getOutputStream();
		if (mOnConnectedListener != null)
			mOnConnectedListener.onConnected(this);
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
		isClose = true;
	}


	/**
	 * Get response of this request.
	 */
	@Override
	public Response response() throws Exception {
		return null;
	}


	/**
	 * Ensure connectioned.
	 */
	protected void ensureConnected() throws ConnectException {
		if (!isConnect)
			throw new ConnectException("Connection abort!");

		if (isClose)
			throw new ConnectException("Connection closed!");

		if (mOutputStream == null)
			throw new ConnectException("The OutputStream or data is null!");
	}


	/**
	 * Request data to somewhere.
	 *
	 * @throws IOException If exception.
	 */
	@Override
	public void send() throws IOException {
		send(mData);
	}


	/**
	 * Request data to somewhere.
	 *
	 * @param data The data.
	 * @throws IOException If exception.
	 */
	@Override
	public void send(byte[] data) throws IOException {
		ensureConnected();
		mOutputStream.write(data);
		isSend = true;
		if (mOnSendListener != null)
			mOnSendListener.onSend(this);
	}


	/**
	 * Reopen the request with last data.
	 */
	@Override
	public void reopen() throws IOException {

	}
}
