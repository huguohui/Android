package com.tankwar.net.http;


import com.tankwar.net.Header;
import com.tankwar.net.Requester;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;

/**
 * HTTP request class implement.
 * @author HGH
 * @since 2015/11/05
 */
public class HttpRequester extends Requester {
    /** The method of requesting  */
    private Http.Method method = Http.Method.GET;

	/** Requested url. */
	private URL mUrl;

	/** Accept field. */
	private final String ACCEPT = "*/*";

	/** User-Agent field. */
	private final String USER_AGENT = "T-Virus v1.0";

	/** Accept-Encoding field. */
	private final String ACCEPT_ENCODING = "idenitiy";

	/** Connection field. */
	private final String CONNECTING = "Close";

	/** The default http version. */
	private final String HTTP_VERSION = "1.1";



	/**
     * Construct a http request object.
     * @param url Special URL.
     */
	public HttpRequester(URL url, Http.Method method) throws IOException {
		super(new InetSocketAddress(InetAddress.getByName(url.getHost()).getHostAddress(),
				url.getPort() == -1 ? 80 : url.getPort()));
		if (url == null)
			throw new NullPointerException("The URL can't null!");

		if (method != null)
			this.method = method;

		mUrl = url;
	}


	/**
	 * Default constructor.
	 */
	public HttpRequester() {
		setHeader(new HttpHeader(true));
	}


	/**
	 * Build default http header.
	 * @return Default header.
	 */
	private HttpHeader buildDefaultHeader() {
		HttpHeader header = new HttpHeader(true);
		header.setMethod(method);
		header.setVersion(HTTP_VERSION);
		header.setUrl(getUrl().getPath());
		header.append("Accept", ACCEPT).append("Accept-Encoding", ACCEPT_ENCODING)
			  .append("User-Agent", USER_AGENT).append("Connecting", CONNECTING)
			  .append("Host", mUrl.getHost());
		return header;
	}


	/**
	 * Build http header.
	 */
	private void buildHeader() {
		if (getHeader() == null) {
			setHeader(buildDefaultHeader());
		}else{
			Header header = buildDefaultHeader();
			header.appendAll(getHeader().getContent());
			setHeader(header);
		}
	}


    /**
     * Sends http request.
     */
    @Override
    public boolean send() throws IOException {
		OutputStream os = getSocket().getOutputStream();
		boolean isSent = false;
		buildHeader();
		if (send(getHeader().toString().getBytes(), os)) {
			if (getBody() != null) {
				if (send(getBody().getContent(), os)) {
					isSent = true;
				}
			}
			isSent = true;
		}

		return isSent;
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
	 * Set header.
	 */
	public void setHeader(Header header) {
		if (getHeader() != null)
			getHeader().appendAll(header.getContent());
		else
			super.setHeader(header);
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
