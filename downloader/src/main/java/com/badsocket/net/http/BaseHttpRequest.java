package com.badsocket.net.http;

import com.badsocket.net.AbstractRequest;
import com.badsocket.net.Entity;
import com.badsocket.net.Header;
import com.badsocket.net.Request;
import com.badsocket.net.Response;
import com.badsocket.net.http.Http.Method;
import com.badsocket.net.newidea.URI;
import com.badsocket.util.Log;
import com.badsocket.util.URLUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * HTTP request class implement.
 *
 * @author HGH
 * @since 2015/11/05
 */
public class BaseHttpRequest extends AbstractRequest implements HttpRequest {
	/**
	 * Accept field.
	 */
	protected final String ACCEPT = "*/*";

	/**
	 * User-Agent field.
	 */
	protected final String USER_AGENT = "T-Virus v1.0";

	/**
	 * Accept-Encoding field.
	 */
	protected final String ACCEPT_ENCODING = "identity";

	/**
	 * Connection field.
	 */
	protected final String CONNECTING = "close";

	/**
	 * The default http version.
	 */
	protected final String HTTP_VERSION = "1.1";

	/**
	 * The method of requesting
	 */
	protected Method mMethod = Method.GET;

	protected HttpResponse mHttpResponse;

	protected URI mUri;

	/**
	 * Construct a http request object.
	 *
	 * @param uri    Special URL.
	 * @param method Special method of requesting.
	 */
	public BaseHttpRequest(URI address, Method method) throws IOException {
		if (method != null)
			this.mMethod = method;

		mUri = address;
		mAddress = URLUtils.socketAddressByUri(address);
		initHeader();
	}

	/**
	 * Construct a http request object.
	 *
	 * @param uri Special URL.
	 */
	public BaseHttpRequest(URI address) throws IOException {
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
	 *
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
		mHeader.setUrl(mUri.toString());
		mHeader.setMethod(mMethod);
		mHeader.set(Http.HOST, URLUtils.domainWithPort(mUri));
	}

	protected void afterSend() {
		System.out.println("sent !!!!!!!!!!");
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
		if (mOnResponseListener != null) {
			mOnResponseListener.onResponse(mHttpResponse);
		}
	}


	public void open(URI uri, Method method) throws IOException {
		setUri(uri);
		setMethod(method == null ? Method.GET : method);
		super.open(URLUtils.socketAddressByUri(uri));
	}

	public void open(URI uri) throws IOException {
		open(uri, null);
	}

	/**
	 * Reopen a connection.
	 */
	public void reopen(InetSocketAddress uri) throws IOException {
		mAddress = uri;
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
		receiveResponse();
		return mHttpResponse == null ? mHttpResponse = new HttpResponse(this) : mHttpResponse;
	}

	public Method getMethod() {
		return mMethod;
	}

	public void setMethod(Method method) {
		this.mMethod = method;
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

		public Builder setAddress(InetSocketAddress address) {
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

	public URI getUri() {
		return mUri;
	}

	public BaseHttpRequest setUri(URI uri) {
		mUri = uri;
		return this;
	}
}
