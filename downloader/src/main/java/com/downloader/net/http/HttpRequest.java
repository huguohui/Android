package com.downloader.net.http;


import com.downloader.net.AbstractSocketRequest;
import com.downloader.net.SocketHeader;
import com.downloader.net.SocketResponse;
import com.downloader.net.http.Http.Method;
import com.downloader.util.Log;
import com.downloader.util.UrlUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;

/**
 * HTTP request class implement.
 * @author HGH
 * @since 2015/11/05
 */
public class HttpRequest extends AbstractSocketRequest {
	/** Accept field. */
	protected final String ACCEPT = "*/*";

	/** User-Agent field. */
	protected final String USER_AGENT = "T-Virus v1.0";

	/** Accept-Encoding field. */
	protected final String ACCEPT_ENCODING = "identity";

	/** Connection field. */
	protected final String CONNECTING = "Close";

	/** The default http version. */
	protected final String HTTP_VERSION = "1.1";

	/** The method of requesting  */
	protected Method mMethod = Method.GET;

	/** Requested url. */
	protected URL mUrl;

	protected HttpResponse mHttpResponse;


	/**
     * Construct a http request object.
     * @param url Special URL.
     * @param method Special method of requesting.
     */
	public HttpRequest(URL url, Method method) throws IOException {
		super(UrlUtil.socketAddressByUrl(url));
		if (method != null)
			this.mMethod = method;

		mUrl = url;
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
	}


	/**
	 * Build default http header.
	 * @return Default header.
	 */
	protected HttpHeader getDefaultHeader() {
		HttpHeader header = new HttpHeader();
		header.setMethod(mMethod);
		header.setVersion(HTTP_VERSION);
		header.set("Accept", ACCEPT).set("Accept-Encoding", ACCEPT_ENCODING)
			  .set("User-Agent", USER_AGENT).set("Connecting", CONNECTING);

		if (mUrl != null) {
			header.setUrl(UrlUtil.urlFullPathParam(mUrl));
			header.set("Host", UrlUtil.domainWithPort(mUrl));
		}

		return header;
	}


	/**
     * Sends http request.
     */
    @Override
    public synchronized void send() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(getHeader().toString().getBytes());
		HttpEntity entity = (HttpEntity) getEntity();
		if (entity != null && entity.getContent() != null) {
			if (entity.getType() == HttpEntity.T_TEXT) {
				bos.write(entity.getContent());
			}
			else {
				entity.getContent(new BufferedOutputStream(mOutputStream));
			}

		}

		send(bos.toByteArray());
		setState(State.sent);
		isSend = true;
		if (mOnSendListener != null) {
			mOnSendListener.onSend(this);
		}

		receiveResponse();
    }


    public void send(byte[] data) throws IOException {
		ensureConnected();
		mOutputStream.write(data);
	}


	@Override
	public Socket socket() throws IOException {
		return mSocket;
	}


	protected void receiveResponse() throws IOException {
		mHttpResponse = new HttpResponse(this);
		if (mOnResponseListener != null) {
			mOnResponseListener.onResponse(mHttpResponse);
		}
	}
	
	
	/**
	 * Open a url address.
	 * @throws IOException If exception.
	 */
	public void open(URL url, Method method) throws IOException {
		setUrl(url);
		setMethod(method == null ? Method.GET : method);
		setHeader(getDefaultHeader());
		open(UrlUtil.socketAddressByUrl(url));
	}
	
	
	/**
	 * Open a url address.
	 * @throws IOException If exception.
	 */
	public void open(URL url) throws IOException {
		open(url, mMethod);
	}


	public void open() throws IOException {
		open(mUrl, mMethod);
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
    	if (!getState().equals(State.closed)) {
			close();
		}
    	
    	isSend = false;
    	open(mUrl);
	}
	

    public Method getMethod() {
        return mMethod;
    }

    public void setMethod(Method method) {
        this.mMethod = method;
    }


	public URL getUrl() {
		return mUrl;
	}
	
	
	public void setUrl(URL url) {
		if (url == null)
			throw new NullPointerException("The special URL can't null!");

		mUrl = url;
	}


	public SocketResponse response() throws IOException {
		return mHttpResponse == null ? mHttpResponse = new HttpResponse(this) : mHttpResponse;
	}
}
