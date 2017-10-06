package com.downloader.net.http;


import com.downloader.net.AbstractSocketRequest;
import com.downloader.net.SocketEntity;
import com.downloader.net.SocketHeader;
import com.downloader.net.SocketRequest;
import com.downloader.net.SocketResponse;
import com.downloader.net.WebAddress;
import com.downloader.net.http.Http.Method;
import com.downloader.util.Log;
import com.downloader.util.UrlUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Scanner;

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
	protected final String CONNECTING = "close";

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
	public HttpRequest(WebAddress address, Method method) throws IOException {
		super(UrlUtil.socketAddressByUrl(address.getUrl()));
		if (method != null)
			this.mMethod = method;


		mUrl = address.getUrl();
		this.mAddress = address;
		initHeader();
	}

	
	/**
     * Construct a http request object.
     * @param url Special URL.
     */
	public HttpRequest(WebAddress address) throws IOException {
		this(address, null);
	}


	/**
	 * Default constructor.
	 */
	public HttpRequest() {
		initHeader();
	}


	/**
	 * Build default http header.
	 * @return Default header.
	 */
	protected void initHeader() {
		HttpHeader mHeader = new HttpHeader();
		mHeader.setVersion(HTTP_VERSION);
		mHeader.set(Http.ACCEPT, ACCEPT).set(Http.ACCEPT_ENCODING, ACCEPT_ENCODING)
			  .set(Http.USER_AGENT, USER_AGENT).set(Http.CONNECTION, CONNECTING);

		this.mHeader = mHeader;
	}


	protected void beforeSend() {
		HttpHeader mHeader = (HttpHeader) this.mHeader;
		mHeader.setUrl(mUrl.toString());
		mHeader.setMethod(mMethod);
		mHeader.set(Http.HOST, UrlUtil.domainWithPort(mUrl));
	}


	/**
     * Sends http request.
     */
    @Override
    public synchronized void send() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		HttpEntity entity = (HttpEntity) getEntity();

		beforeSend();
		bos.write(getHeader().toString().getBytes());
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
		super.open(UrlUtil.socketAddressByUrl(url));
	}
	
	
	/**
	 * Open a url address.
	 * @throws IOException If exception.
	 */
	public void open(SocketAddress url) throws IOException {
		WebAddress a = (WebAddress) url;
		open(a.getUrl(), mMethod);
	}


	public void open() throws IOException {
		open(mUrl, mMethod);
	}


	/**
	 * Reopen a connection.
	 */
	public void reopen(SocketAddress url) throws IOException {
		WebAddress address = (WebAddress) url;
    	mUrl = address.getUrl();
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
    	open(mAddress);
	}


	public SocketResponse response() throws IOException {
		return mHttpResponse == null ? mHttpResponse = new HttpResponse(this) : mHttpResponse;
	}


	public void setAddress(SocketAddress address) {
		if (address == null) {
			throw new NullPointerException("The requesting address is null!");
		}

		mAddress = address;
		mUrl = ((WebAddress) address).getUrl();
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


	public void setHeader(String key, String val) {
		HttpHeader header = (HttpHeader) mHeader;
		header.set(key, val);
	}


	public String getHeader(String key) {
		return ((HttpHeader) mHeader).get(key);
	}


	public static class Range extends SocketRequest.Range {
		public Range(long s, long e) {
			super(s, e);
		}


		public Range(SocketRequest.Range r) {
			super(r.start, r.end);
		}


		public String toString() {
			return end > 0 ? String.format("bytes=%d-%d", start, end)
					: String.format("bytes=%d-", start);
		}
	}


	public static class Builder implements SocketRequest.RequestBuilder {

		private HttpRequest request;

		public Builder() {
			request = new HttpRequest();
		}


		public Builder setAddress(WebAddress address) {
			request.setAddress(address);
			return this;
		}


		public Builder setMethod(Method method) {
			request.setMethod(method);
			return this;
		}


		public Builder setHeader(SocketHeader header) {
			request.setHeader(header);
			return this;
		}


		public Builder setTimeout(int t) {
			request.setTimeout(t);
			return this;
		}


		public Builder setEntity(SocketEntity t) {
			request.setEntity(t);
			return this;
		}


		@Override
		public SocketRequest build() {
			return request;
		}
	}
}
