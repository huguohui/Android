package com.downloader.net.http;


import com.downloader.net.AbsReceiver;
import com.downloader.net.Receive;
import com.downloader.net.Request;
import com.downloader.net.http.Http.Method;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * HTTP request class implement.
 * @author HGH
 * @since 2015/11/05
 */
public class HttpRequest extends Request {
    /** The method of requesting  */
    private Http.Method mMethod = Http.Method.GET;

	/** Requested url. */
	private URL mUrl;

	/** Accept field. */
	private final String ACCEPT = "*/*";

	/** User-Agent field. */
	private final String USER_AGENT = "T-Virus v1.0";

	/** Accept-Encoding field. */
	private final String ACCEPT_ENCODING = "identity";

	/** Connection field. */
	private final String CONNECTING = "Close";

	/** The default http version. */
	private final String HTTP_VERSION = "1.1";


	/**
     * Construct a http request object.
     * @param url Special URL.
     */
	public HttpRequest(URL url, Http.Method method) throws IOException {
		super(getSocketAddressByUrl(url));
		if (url == null)
			throw new NullPointerException("The URL can't null!");

		if (method != null)
			this.mMethod = method;

		mUrl = url;
		setHeader(getDefaultHeader());
	}


	/**
	 * Default constructor.
	 */
	public HttpRequest() {
		setHeader(getDefaultHeader());
	}


	/**
	 * Build default http header.
	 * @return Default header.
	 */
	private HttpHeader getDefaultHeader() {
		HttpHeader header = new HttpHeader();
		header.setMethod(mMethod);
		header.setVersion(HTTP_VERSION);
		header.add("Accept", ACCEPT).add("Accept-Encoding", ACCEPT_ENCODING)
			  .add("User-Agent", USER_AGENT).add("Connecting", CONNECTING);
		if (mUrl != null) {
			header.setUrl(getUrlFullPath(mUrl));
			header.add("Host", getDomainWithPort(mUrl));
		}
		return header;
	}


	/**
	 * Get the top domain of url.
	 */
	public static String getTopDomain(URL url) {
		if (url == null)
			return null;

		String host = url.getHost();
		return "www." + host.substring(host.substring(0, host.lastIndexOf('.')).lastIndexOf('.') + 1);
	}


	/**
	 * Get IP address by top domain.
	 * @param domain Top domain.
	 * @return IP address of domain.
	 * @throws UnknownHostException Can't parse domain.
	 */
	public static InetAddress getInetAddressByDomain(String domain) throws UnknownHostException {
		return InetAddress.getByName(domain);
	}


	/**
	 * Get socket address by URL.
	 * @param url The URL.
	 * @return Socket address.
	 * @throws UnknownHostException
	 */
	public static InetSocketAddress getSocketAddressByUrl(URL url) throws UnknownHostException {
		return new InetSocketAddress(getInetAddressByDomain(url.getHost()),
				url.getPort() == -1 ? 80 : url.getPort());
	}

	
	/**
	 * Get URL path and query string.
	 */
	public static String getUrlFullPath(URL url) {
		if (url == null) return null;
		return (url.getPath() == null || url.getPath().equals("") ?
				"/" + url.getPath() : url.getPath())
				+ (url.getQuery() == null ? "" : "?" + url.getQuery())
				+ (url.getRef() == null ? "" : "#" +url.getRef());
	}
	
	
	/**
	 * Get domain with port.
	 */
	public static String getDomainWithPort(URL url) {
		if (url == null) return null;
		return url.getHost() + ":" + (url.getPort() != -1 ? url.getPort() : Http.DEFAULT_PORT);
	}


    /**
     * Sends http request.
     */
    @Override
    public synchronized void send() throws IOException {
		OutputStream os = getSocket().getOutputStream();
		boolean isSent = false;

		send(getHeader().toString().getBytes(), os);
		if (getBody() != null)
			send(getBody().getContent(), os);

		isSent = true;
		setState(State.sent);
		getHeader().setContent(getSocket().getInputStream());
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
	public synchronized void send(byte[] data, OutputStream to) throws IOException {
		if (to == null || data == null)
			throw new RuntimeException("The OutputStream or data is null!");

		to.write(data);
	}
	
	
	/**
	 * Open a url address.
	 * @throws IOException If exception.
	 */
	public void open(URL url, Http.Method method) throws IOException {
		setUrl(url);
		setMethod(method == null ? Method.GET : method);
		open(getSocketAddressByUrl(url));
	}
	
	
	/**
	 * Open a url address.
	 * @throws IOException If exception.
	 */
	public void open(URL url) throws IOException {
		open(url, null);
	}


    public Http.Method getMethod() {
        return mMethod;
    }

    public void setMethod(Http.Method method) {
        this.mMethod = method;
    }


	public URL getUrl() {
		return mUrl;
	}


    /**
     * Sets the url of requesting.
     * @param url
     */
	public void setUrl(URL url) {
		if (url == null)
			throw new NullPointerException("The special URL can't null!");

		mUrl = url;
		((HttpHeader)getHeader()).setUrl(getUrlFullPath(mUrl));
		getHeader().add("Host", getDomainWithPort(mUrl));
	}


	 /**
     * Get a downloader of this request.
     * return A downloader of this request.
	 * @throws IOException 
     */
	@Override
	public AbsReceiver getDownloader() throws IOException {
		return new HttpReceiver(this);
	}
}
