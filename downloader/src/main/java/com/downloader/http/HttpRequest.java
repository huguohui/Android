package com.downloader.http;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import com.downloader.base.AbstractRequest;
import com.downloader.http.Http.Method;
import com.downloader.util.UrlUtil;

/**
 * HTTP request class implement.
 * @author HGH
 * @since 2015/11/05
 */
public class HttpRequest extends AbstractRequest {
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
     * @param method Special method of requesting.
     */
	public HttpRequest(URL url, Http.Method method) throws IOException {
		super(UrlUtil.getSocketAddressByUrl(url));
		if (url == null)
			throw new NullPointerException("The URL can't null!");

		if (method != null)
			this.mMethod = method;

		mUrl = url;
		setHeader(getDefaultHeader());
	}
	
	
	/**
     * Construct a http request object.
     * @param url Special URL.
     */
	public HttpRequest(URL url) throws IOException {
		this(url, null);
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
			header.setUrl(UrlUtil.getUrlFullPath(mUrl));
			header.add("Host", UrlUtil.getDomainWithPort(mUrl));
		}
		return header;
	}


    /**
     * Sends http request.
     */
    @Override
    public synchronized void send() throws IOException {
		OutputStream os = getSocket().getOutputStream();
		send(getHeader().toString().getBytes(), os);
		if (getBody() != null)
			send(getBody().getContent(), os);

		setState(State.sent);
    }


	/**
	 * Request data to somewhere.
	 *
	 * @param data The data.
	 * @param to   Request to somewhere.
	 * @return If sent return true, else false.
	 * @throws IOException If exception.
	 */
	@Override
	public synchronized void send(byte[] data, OutputStream to) throws IOException {
    	super.send();
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
		open(UrlUtil.getSocketAddressByUrl(url));
	}
	
	
	/**
	 * Open a url address.
	 * @throws IOException If exception.
	 */
	public void open(URL url) throws IOException {
		open(url, null);
	}
	

	/**
     * Get a downloader of this request.
     * return A downloader of this request.
	 * @throws IOException 
     */
	@Override
	public HttpReceiver getReceiver() throws IOException {
		return new HttpReceiver(this);
	}


	/**
	 * Reopen a connection.
	 */
	public void reopen(URL url) throws IOException {
    	mUrl = url;
    	reopen();
	}
	
	
	/**
	 * Reopen a connection.
	 */
	public void reopen() throws IOException {
    	if (!getState().equals(State.closed))
    		close();
    	
    	setSend(false);
    	open(mUrl);
	}
	
	
	/**
	 * 
	 * @return
	 */


    public Http.Method getMethod() {
        return mMethod;
    }

    public void setMethod(Http.Method method) {
        this.mMethod = method;
    }


	public URL getUrl() {
		return mUrl;
	}
	
	
	public void setUrl(URL url) {
		if (url == null)
			throw new NullPointerException("The special URL can't null!");

		mUrl = url;
		((HttpHeader)getHeader()).setUrl(UrlUtil.getUrlFullPath(mUrl));
		getHeader().add("Host", UrlUtil.getDomainWithPort(mUrl));
	}
}
