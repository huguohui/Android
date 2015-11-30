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
public class HttpRequester extends Requester {
    /** The method of requesting http. */
    private Http.Method method = Http.Method.GET;

	/** Requested url. */
	private URL mUrl;

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
	public HttpRequester(URL url, Http.Method method) throws IOException {
		super();
		if (url == null)
			throw new NullPointerException("The URL can't null!");

        if (method != null)
			this.method = method;
		mUrl = url;
		open();
	}


	/**
	 * Default constructor.
	 */
	public HttpRequester() {}


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
		setHeader(header);
	}


    /**
     * Sends http request.
     */
    @Override
    public boolean send() throws IOException {
		OutputStream os = getSocket().getOutputStream();
		if (send(getHeader().toString().getBytes(), os)) {
			if (send(getBody().getContent(), os)) {
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


    public Http.Method getMethod() {
        return method;
    }

    public void setMethod(Http.Method method) {
        this.method = method;
    }


	public URL getUrl() {
		return mUrl;
	}


	public void setUrl(URL url) {
		mUrl = url;
	}
}
