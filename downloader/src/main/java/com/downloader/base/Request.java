package com.downloader.base;


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
public abstract class Request implements Send {
    /** The requests address. */
    private SocketAddress mSocketAddress;

    /** The socket of requester. */
    private Socket mSocket;

    /** The connection timeout. (ms) */
    private int mTimeout = 100000;

    /** Http header. */
    private Header mHeader;

    /** Http body. */
    private Body mBody;

    /** The state of requester. */
    private State mState = State.ready;

	/** The requester start time. */
	private long mStartTime = 0;
	
	/** State of connection by boolean. */
	private boolean isConnect = false;
	
	/** Flag for reqeust send. */
	private boolean isSend = false;
	
	/** Address of reqeust. */
	private String mAddress = "";

    /** Request states. */
    public enum State {
        ready,      // Ready to requesting.
        connecting, // Connecting...
        connected,  // Connected to target host.
        sending, 	// Sending data...
        sent,       // Requesting was sent.
        closed      // Connecting was closed.
    };


    /**
     * Constructor a request by socket address.
     * @param  socketAddress The requests address.
     */
    public Request(SocketAddress socketAddress) throws IOException {
        setSocketAddress(socketAddress);
        open(socketAddress, mTimeout);
    }


    /**
     * Empty constructor.
     */
    public Request() {
    }
    
    
    /**
     * Get a downloader of this request.
     * return A {@link AbstractReceiver} of this request.
     */
    public abstract AbstractReceiver getReceiver() throws IOException;


    /**
     * Open a connection.
     */
    public void open() throws IOException {
        open(mSocketAddress);
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
    }
    
    
    /**
     * Send request.
     */
    public void send() throws IOException {
    	isSend = true;
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
     * Reopen the request with last data.
     */
    public abstract void reopen() throws IOException;
    

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
	
	
	public boolean isConnect() {
		return isConnect;
	}


	public void setConnect(boolean isConnetion) {
		this.isConnect = isConnetion;
	}


	public String getAddress() {
		return mAddress;
	}


	public void setAddress(String address) {
		mAddress = address;
	}


	public boolean isSend() {
		return isSend;
	}


	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}


	/**
	 * Request state listener.
	 */
	public interface Listener {
	}
}
