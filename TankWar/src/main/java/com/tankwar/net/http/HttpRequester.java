package com.tankwar.net.http;

import com.tankwar.net.RequestStateListener;
import com.tankwar.net.Requester;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

/**
 * HTTP request class implement.
 * @author HGH
 * @since 2015/11/05
 */
public class HttpRequester implements Requester {
    /** The method of requesting http. */
    private Http.Method method = Http.Method.GET;

    /** Connection timeout time. Default time is 3s. */
    private int timeout = 3000;

    /** Requester state //listeners. */
    private RequestStateListener listener;

    /** The base socket object of request. */
    private Socket mSocket;

	/** Accept field. */
	private final String ACCEPT = "*/*";

	/** User-Agent field. */
	private final String USER_AGENT = "T-Virus v1.0";

	/** Accept-Encoding field. */
	private final String ACCEPT_ENCODING = "";

	/** Connection field. */
	private final String CONNECTING = "close";

	/** The default http version. */
	private final String HTTP_VERSION = "1.1";



	/**
     * Construct a http request object.
     * @param url Special URL.
     */
	public HttpRequester(URL url, Http.Method method) throws NullPointerException {
        if (method == null)
			throw new NullPointerException("HTTP request can't null!");

        this.method = method;
		open();
	}


	/**
	 * Initialize some prepare work.
	 */
	private void initialize() {
		HttpHeader header = new HttpHeader(true);
		header.setMethod(method);
		header.setVersion(HTTP_VERSION);
		header.setUrl(getUrl().getPath());
		header.append("Accept", ACCEPT).append("Accept-Encoding", ACCEPT_ENCODING)
			  .append("User-Agent", USER_AGENT).append("Connecting", CONNECTING);
	}


    /**
     * Sends http request.
     */
    @Override
    public boolean send() throws IOException {
		OutputStream os = mSocket.getOutputStream();
		if (send(getHttpHeader().toString().getBytes(), os)) {
			if (send(getHttpBody().getContent(), os)) {
				listener.onSended();
				return true;
			}
		}

		listener.onSendFailed();
		return false;
    }


	/**
	 * Send data to somewhere.
	 *
	 * @param data The data.
	 * @param to   Send to somewhere.
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	@Override
	public boolean send(byte[] data, OutputStream to) throws IOException {
		to.write(data);
		return true;
	}


	/**
	 * Open a connection.
	 */
	@Override
	public boolean open() {

		return false;
	}


	/**
	 * Open a connection and prepare to send request.
	 *
	 * @param address
	 * @return If connect success, return true else false.
	 */
	@Override
	public boolean open(InetSocketAddress address) {
		return false;
	}


	/**
	 * Open a connection with timeout then prepare to send request.
	 *
	 * @param address
	 * @param timeout
	 */
	@Override
	public boolean open(InetSocketAddress address, int timeout) {
		return false;
	}


	/**
     * Close http connection.
     */
    @Override
    public void close() {
        try{
            mSocket.shutdownInput();
            mSocket.shutdownOutput();
            mSocket.close();
        }catch(IOException ex) {

        }
    }


    public Http.Method getMethod() {
        return method;
    }

    public void setMethod(Http.Method method) {
        this.method = method;
    }


    public void setListener(RequestStateListener listener) {
		this.listener = listener;
    }
}
