package com.badsocket.net;


import com.badsocket.util.TimeUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Describes a request.
 *
 * @author HGH
 * @since 2015/11/04
 */
public abstract class AbstractSocketRequest implements SocketRequest {
    /** The requests address. */
    protected SocketAddress mAddress;

    /** The socket of requester. */
    protected Socket mSocket;

    /** The connection timeout. (ms) */
    protected int mTimeout = 200000;

	/** The connection used time. */
	protected long mConnectionTime;

    /** Http header. */
    protected SocketHeader mHeader;

    /** Http body. */
    protected SocketEntity mEntity;

    /** The state of requester. */
    protected State mState = State.ready;

	/** The requester start time. */
	protected long mStartTime = 0;
	
	/** State of connection by boolean. */
	protected boolean isConnect = false;

	protected boolean isClose = false;
	
	/** Flag for reqeust send. */
	protected boolean isSend = false;

	/** Data will send. */
	protected byte[] mData;

	protected OutputStream mOutputStream;

	protected OnConnectedListener mOnConnectedListener;

	protected OnSendListener mOnSendListener;

	protected OnResponseListener mOnResponseListener;

	protected SocketRequest.Range range;



	/** AbstractSocketRequest states. */
    public enum State {
        ready,      // Ready to requesting.
        connecting, // Connecting...
        connected,  // Connected to target host.
        sending, 	// Sending data...
        sent,       // Requesting was sent.
        closed      // Connecting was closed.
    };



	public AbstractSocketRequest(SocketAddress socketAddress) throws IOException {
		setAddress(socketAddress);
		open(socketAddress, mTimeout);
	}


	/**
	 * Empty constructor.
	 */
	public AbstractSocketRequest() {}


    /**
     * Open a connection.
     */
    public void open() throws IOException {
        open(mAddress);
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
		mConnectionTime = TimeUtils.millisTime() - mStartTime;
		mOutputStream = mSocket.getOutputStream();

		if (mOnConnectedListener != null) {
			mOnConnectedListener.onConnected(this);
		}
	}


	/**
	 * Close connection.
	 */
	public void close() throws IOException {
		mState = State.closed;
		if (mSocket.isClosed() || !mSocket.isConnected()) {
			throw new ConnectException("The socket don't connected!");
		}

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
	public SocketResponse response() throws IOException {
		return null;
	}


	/**
	 * Ensure connectioned.
	 */
	protected void ensureConnected() throws ConnectException {
		if (!isConnect) {
			throw new ConnectException("Connection abort!");
		}
		if (isClose) {
			throw new ConnectException("Connection closed!");
		}
		if (mOutputStream == null) {
			throw new ConnectException("The OutputStream or data is null!");
		}
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
		if (mOnSendListener != null) {
			mOnSendListener.onSend(this);
		}
	}


	/**
	 * Reopen the request with last data.
	 */
	@Override
	public void reopen(SocketAddress address) throws IOException {
		setAddress(address);
		reopen();
	}


	public Range getRange() {
		return range;
	}


	public AbstractSocketRequest setRange(Range range) {
		this.range = range;
		return this;
	}


	public boolean sent() {
		return isSend;
	}


	public boolean connected() {
		return isConnect;
	}


	public boolean closed() {
		return isClose;
	}
    

    public SocketAddress getAddress() {
        return mAddress;
    }
    

    public void setAddress(SocketAddress address) {
        if (address == null) {
			throw new NullPointerException("The requesting address is null!");
		}

        mAddress = address;
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


    public SocketHeader getHeader() {
        return mHeader;
    }


    public void setHeader(SocketHeader header) {
        mHeader = header;
    }


    public SocketEntity getEntity() {
        return mEntity;
    }


    public void setEntity(SocketEntity entity) {
        mEntity = entity;
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


	public OutputStream getOutputStream() {
		return mOutputStream;
	}


	public AbstractSocketRequest setOnConnectedListener(OnConnectedListener onConnectedListener) {
		mOnConnectedListener = onConnectedListener;
		return this;
	}


	public AbstractSocketRequest setOnSendListener(OnSendListener onSendListener) {
		mOnSendListener = onSendListener;
		return this;
	}



	public AbstractSocketRequest setOnResponseListener(OnResponseListener onResponseListener) {
		mOnResponseListener = onResponseListener;
		return this;
	}


	public long getConnectionTime() {
		return mConnectionTime;
	}
}
