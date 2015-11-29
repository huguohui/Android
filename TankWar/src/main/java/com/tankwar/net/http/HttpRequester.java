package com.tankwar.net.http;

import com.tankwar.net.Requester;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
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

    /** The base socket object of request. */
    private Socket mSocket;

	/** Requested url. */
	private URL mUrl;

	/** Http header. */
	private HttpHeader mHttpHeader;

	/** Http body. */
	private HttpBody mHttpBody;

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
	public HttpRequester(URL url, Http.Method method) throws NullPointerException, IOException {
        if (url == null)
			throw new NullPointerException("The URL can't null!");

        if (method != null)
			this.method = method;
		mUrl = url;
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
				return true;
			}
		}

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
	 * Set requests address.
	 *
	 * @param address Requests address.
	 */
	@Override
	public void setAddress(InetSocketAddress address) {

	}


	/**
	 * Get requests address.
	 *
	 * @return Requests address.
	 */
	@Override
	public InetSocketAddress getAddress() {
		return null;
	}


	/**
	 * Open a connection.
	 */
	@Override
	public boolean open() throws IOException {
		InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(
				mUrl.getHost()), getUrl().getPort());
		return open(socketAddress);
	}


	/**
	 * Open a connection and prepare to send request.
	 *
	 * @param address
	 * @return If connect success, return true else false.
	 */
	@Override
	public boolean open(InetSocketAddress address) throws IOException {
		return open(address, timeout);
	}


	/**
	 * Open a connection with timeout then prepare to send request.
	 *
	 * @param address
	 * @param timeout
	 */
	@Override
	public boolean open(InetSocketAddress address, int timeout) throws IOException {
		mSocket.connect(address, timeout);
		return true;
	}


	/**
     * Close http connection.
     */
    @Override
    public void close() throws IOException {
		mSocket.shutdownInput();
		mSocket.shutdownOutput();
		mSocket.close();
	}


    public Http.Method getMethod() {
        return method;
    }

    public void setMethod(Http.Method method) {
        this.method = method;
    }


	public int getTimeout() {
		return timeout;
	}


	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	public Socket getSocket() {
		return mSocket;
	}


	public void setSocket(Socket socket) {
		mSocket = socket;
	}


	public URL getUrl() {
		return mUrl;
	}


	public void setUrl(URL url) {
		mUrl = url;
	}


	public HttpHeader getHttpHeader() {
		return mHttpHeader;
	}


	public void setHttpHeader(HttpHeader httpHeader) {
		mHttpHeader = httpHeader;
	}


	public HttpBody getHttpBody() {
		return mHttpBody;
	}


	public void setHttpBody(HttpBody httpBody) {
		mHttpBody = httpBody;
	}
}
