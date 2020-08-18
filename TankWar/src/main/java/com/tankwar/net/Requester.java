package com.tankwar.net;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Describe a request.
 *
 * @author HGH
 * @since 2015/11/04
 */
public abstract class Requester implements Sender {
	/**
	 * The requests address.
	 */
	private SocketAddress mSocketAddress;

	/**
	 * The socket of requester.
	 */
	private Socket mSocket;

	/**
	 * The connection timeout. (ms)
	 */
	private int mTimeout = 100000;

	/**
	 * Http header.
	 */
	private Header mHeader;

	/**
	 * Http body.
	 */
	private Body mBody;

	/**
	 * The state of requester.
	 */
	private State mState = State.ready;

	/**
	 * The requester start time.
	 */
	private long mStartTime = 0;

	/**
	 * Request states.
	 */
	public enum State {
		ready,      // Ready to requesting.
		connected,  // Connected to target host.
		sent,       // Requesting was sent.
		closed      // Connecting was closed.
	}

	;

	/**
	 * Constructor a request by socket address.
	 *
	 * @param socketAddress The requests address.
	 */
	public Requester(SocketAddress socketAddress) throws IOException {
		setSocketAddress(socketAddress);
		open(socketAddress, mTimeout);
		mState = State.connected;
	}

	/**
	 * Empty constructor.
	 */
	public Requester() {
	}

	/**
	 * Open a connection.
	 */
	public boolean open() throws IOException {
		return open(mSocketAddress);
	}

	/**
	 * Open a connection and prepare to send request.
	 *
	 * @return If connect success, return true else false.
	 */
	public boolean open(SocketAddress address) throws IOException {
		return open(address, mTimeout);
	}

	/**
	 * Open a connection with timeout then prepare to send request.
	 */
	public boolean open(SocketAddress address, int timeout)
			throws IOException {
		if (mState.equals(State.closed) || mState.equals(State.ready)) {
			mStartTime = System.currentTimeMillis();
			mSocket = new Socket();
			mSocket.connect(address, timeout);
			return true;
		}
		throw new ConnectException("Only open connection after requester " +
				"closed or requester don't connected!");
	}

	/**
	 * Close connection.
	 */
	public void close() throws IOException {
		if (mSocket.isClosed() || !mSocket.isConnected())
			throw new ConnectException("The socket don't connected!");

		mSocket.shutdownInput();
		mSocket.shutdownOutput();
		mSocket.close();
	}

	public SocketAddress getSocketAddress() {
		return mSocketAddress;
	}

	public void setSocketAddress(SocketAddress socketAddress) {
		if (socketAddress == null)
			throw new NullPointerException("The requesting address is null!");
		mSocketAddress = socketAddress;
	}

	public Socket getSocket() {
		return mSocket;
	}

	public void setSocket(Socket socket) {
		mSocket = socket;
	}

	public int getTimeout() {
		return mTimeout;
	}

	public void setTimeout(int timeout) {
		mTimeout = timeout;
	}

	public Header getHeader() {
		return mHeader;
	}

	public void setHeader(Header header) {
		mHeader = header;
	}

	public Body getBody() {
		return mBody;
	}

	public void setBody(Body body) {
		mBody = body;
	}

	public State getState() {
		return mState;
	}

	public void setState(State state) {
		mState = state;
	}

	public long getStartTime() {
		return mStartTime;
	}

	public void setStartTime(long startTime) {
		mStartTime = startTime;
	}
}
