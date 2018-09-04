package com.badsocket.net.http;


import com.badsocket.net.AbstractRequest;
import com.badsocket.net.DownloadAddress;
import com.badsocket.net.Entity;
import com.badsocket.net.Header;
import com.badsocket.net.Request;
import com.badsocket.net.Response;
import com.badsocket.net.http.Http.Method;
import com.badsocket.util.Log;
import com.badsocket.util.UrlUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

/**
 * HTTP request class implement.
 * @author HGH
 * @since 2015/11/05
 */
public class BaseHttpRequest extends AbstractRequest implements HttpRequest {
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

	/** Requested downloadAddress. */
	protected URL mUrl;

	protected HttpResponse mHttpResponse;

	/**
     * Construct a http request object.
     * @param url Special URL.
     * @param method Special method of requesting.
     */
	public BaseHttpRequest(DownloadAddress address, Method method) throws IOException {
		super(UrlUtils.socketAddressByUrl(address.getUrl()));
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
	public BaseHttpRequest(DownloadAddress address) throws IOException {
		this(address, null);
	}


	/**
	 * Default constructor.
	 */
	public BaseHttpRequest() {
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
		mHeader.set(Http.HOST, UrlUtils.domainWithPort(mUrl));
	}


	protected void afterSend() {
		Log.d("Sent request data.");
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
		afterSend();
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
	 * Open a downloadAddress downloadAddress.
	 * @throws IOException If exception.
	 */
	public void open(InetSocketAddress url, Method method) throws IOException {
		setAddress(url);
		setMethod(method == null ? Method.GET : method);
		super.open(UrlUtils.socketAddressByUrl(((DownloadAddress) url).getUrl()));
		Log.d("Open a connection with url : " + ((DownloadAddress) url).getUrl().toExternalForm());
	}
	
	
	/**
	 * Open a downloadAddress downloadAddress.
	 * @throws IOException If exception.
	 */
	public void open(InetSocketAddress url) throws IOException {
		DownloadAddress adr = (DownloadAddress) url;
		open(adr, mMethod);
	}


	public void open() throws IOException {
		open(mAddress, mMethod);
	}


	/**
	 * Reopen a connection.
	 */
	public void reopen(InetSocketAddress url) throws IOException {
		DownloadAddress address = (DownloadAddress) url;
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


	public synchronized Response response() throws IOException {
		return mHttpResponse == null ? mHttpResponse = new HttpResponse(this) : mHttpResponse;
	}


	public void setAddress(InetSocketAddress address) {
		if (address == null) {
			throw new NullPointerException("The requesting downloadAddress is null!");
		}

		mAddress = address;
		mUrl = ((DownloadAddress) address).getUrl();
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


	public static class Builder implements Request.RequestBuilder {

		private BaseHttpRequest request;

		public Builder() {
			request = new BaseHttpRequest();
		}


		public Builder setAddress(DownloadAddress address) {
			request.setAddress(address);
			return this;
		}


		public Builder setMethod(Method method) {
			request.setMethod(method);
			return this;
		}


		public Builder setHeader(Header header) {
			request.setHeader(header);
			return this;
		}


		public Builder setTimeout(int t) {
			request.setTimeout(t);
			return this;
		}


		public Builder setEntity(Entity t) {
			request.setEntity(t);
			return this;
		}


		@Override
		public Request build() {
			return request;
		}
	}
}
