package com.downloader.net;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Describe a request.
 *
 * @author HGH
 * @since 2015/11/04
 */
public abstract class AbstractRequest implements Request {
    /** The requests address. */
    protected SocketAddress mSocketAddress;

    /** The socket of requester. */
    protected Socket mSocket;

    /** The connection timeout. (ms) */
    protected int mTimeout = 100000;

    /** Http header. */
    protected AbstractHeader mHeader;

    /** Http body. */
    protected AbstractEntity mBody;

    /** The state of requester. */
    protected State mState = State.ready;

	/** The requester start time. */
	protected long mStartTime = 0;
	
	/** State of connection by boolean. */
	protected boolean isConnect = false;

	protected boolean isClose = false;
	
	/** Flag for reqeust send. */
	protected boolean isSend = false;
	
	/** Address of reqeust. */
	protected String mAddress = "";

	/** Data will send. */
	protected byte[] mData;

	protected OutputStream mOutputStream;

    /** AbstractRequest states. */
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
    public AbstractRequest(SocketAddress socketAddress) throws IOException {
        setSocketAddress(socketAddress);
        open(socketAddress, mTimeout);
    }


    /**
     * Empty constructor.
     */
    public AbstractRequest() {
    }


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
    public abstract void open(SocketAddress address, int timeout) throws IOException;
    
    
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


    public AbstractHeader getHeader() {
        return mHeader;
    }


    public void setHeader(AbstractHeader header) {
        mHeader = header;
    }


    public AbstractEntity getBody() {
        return mBody;
    }


    public void setBody(AbstractEntity body) {
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

	public boolean isConnect() {
		return isConnect;
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


	public boolean isClose() {
		return isClose;
	}


	public byte[] getData() {
		return mData;
	}


	public OutputStream getOutputStream() {
		return mOutputStream;
	}


	/**
	 * AbstractRequest state listener.
	 */
	public interface Listener {
	}


	/**
	 * A range of data.
	 */
	final public static class Range {
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
			return -~end - start;
		}


		public String toString() {
			return end > 0 ? String.format("bytes=%d-%d", start, end)
					: String.format("bytes=%d-", start);
		}
	}
}
