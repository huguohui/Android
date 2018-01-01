package com.badsocket.net;


import com.badsocket.util.DateUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.InetSocketAddress;

/**
 * Describes a request.
 *
 * @author HGH
 * @since 2015/11/04
 */
public abstract class AbstractRequest implements Request {
    /** The requests downloadAddress. */
    protected InetSocketAddress mAddress;

    /** The socket of requester. */
    protected Socket mSocket;

    /** The connection timeout. (ms) */
    protected int mTimeout = 200000;

	/** The connection used time. */
	protected long mConnectionTime;

    /** Http header. */
    protected Header mHeader;

    /** Http body. */
    protected Entity mEntity;

    /** The state of requester. */
    protected State mState = State.ready;

	/** The requester start time. */
	protected long mStartTime = 0;
	
	/** TaskState of connection by boolean. */
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

	protected Request.Range range;



	/** AbstractRequest states. */
    public enum State {
        ready,      // Ready to requesting.
        connecting, // Connecting...
        connected,  // Connected to target host.
        sending, 	// Sending data...
        sent,       // Requesting was sent.
        closed      // Connecting was closed.
    };



	public AbstractRequest(InetSocketAddress socketAddress) throws IOException {
		setAddress(socketAddress);
		open(socketAddress, mTimeout);
	}


	/**
	 * Empty constructor.
	 */
	public AbstractRequest() {}


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
	public void open(InetSocketAddress address) throws IOException {
		open(address, mTimeout);
	}


	/**
	 * Open a connection with timeout and prepare to send request.
	 * @param address The {@link InetSocketAddress} to describing a downloadAddress.
	 * @param timeout The timeout time of connection.
	 */
	public void open(InetSocketAddress address, int timeout)
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
		mConnectionTime = DateUtils.millisTime() - mStartTime;
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
	public Response response() throws IOException {
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
	public void reopen(InetSocketAddress address) throws IOException {
		setAddress(address);
		reopen();
	}


	public Range getRange() {
		return range;
	}


	public AbstractRequest setRange(Range range) {
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
    

    public InetSocketAddress getAddress() {
        return mAddress;
    }
    

    public void setAddress(InetSocketAddress address) {
        if (address == null) {
			throw new NullPointerException("The requesting downloadAddress is null!");
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


    public Header getHeader() {
        return mHeader;
    }


    public void setHeader(Header header) {
        mHeader = header;
    }


    public Entity getEntity() {
        return mEntity;
    }


    public void setEntity(Entity entity) {
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


	public AbstractRequest setOnConnectedListener(OnConnectedListener onConnectedListener) {
		mOnConnectedListener = onConnectedListener;
		return this;
	}


	public AbstractRequest setOnSendListener(OnSendListener onSendListener) {
		mOnSendListener = onSendListener;
		return this;
	}



	public AbstractRequest setOnResponseListener(OnResponseListener onResponseListener) {
		mOnResponseListener = onResponseListener;
		return this;
	}


	public long getConnectionTime() {
		return mConnectionTime;
	}
}
