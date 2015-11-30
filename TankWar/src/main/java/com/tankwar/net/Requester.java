package com.tankwar.net;

import com.tankwar.net.http.HttpBody;
import com.tankwar.net.http.HttpHeader;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

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
    private int mTimeout = 3000;

    /** Http header. */
    private Header mHeader;

    /** Http body. */
    private Body mBody;


    /**
     * Constructor a request by socket address.
     *
     * @param  socketAddress The requests address.
     */
    public Requester(SocketAddress socketAddress) throws IOException {
        setSocketAddress(socketAddress);
        open(socketAddress, mTimeout);
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
            throws IOException, SocketTimeoutException {
        mSocket = new Socket();
        mSocket.connect(address, timeout);
        return true;
    }


    /**
     * Close http connection.
     */
    public void close() throws IOException {
        if (mSocket.isClosed() || mSocket.isConnected())
            throw new ConnectException("The socket don't connected!");

        mSocket.shutdownInput();
        mSocket.shutdownOutput();
        mSocket.close();
    }


    public SocketAddress getSocketAddress() {
        return mSocketAddress;
    }

    public void setSocketAddress(SocketAddress socketAddress) {

        if (mSocketAddress == null)
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
}
