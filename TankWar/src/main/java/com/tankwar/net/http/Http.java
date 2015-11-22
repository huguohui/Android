package com.tankwar.net.http;

import java.net.Socket;
import java.net.URL;

/**
 * Describe a Hypertext transfer protocol.
 * @author HGH
 * @since 2015/11/05
 */
public abstract class Http extends Socket {
	/**
	 * A URL special host address and 
	 * request path, parameter.
	 */
	private URL mUrl;

	
	/**
	 * A special HTTP version.
	 */
	private String mVersion;


	/**
	 * HTTP header.
	 */
	private HttpHeader mHttpHeader;

	
	/**
	 * HTTP body.
	 */
	private HttpBody mHttpBody;

    /**
	 * Is security HTTP?
	 */
	private boolean mIsSecurity;
	
	
	/**
	 * Construct a HTTP object by pass a URL.
	 * @param url URL
	 */
	public Http(URL url) {
		mUrl = url;
	}


	public URL getUrl() {
		return mUrl;
	}

	public void setUrl(URL url) {
		mUrl = url;
	}

	public String getVersion() {
		return mVersion;
	}

	public void setVersion(String version) {
		mVersion = version;
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

	public boolean isSecurity() {
		return mIsSecurity;
	}

	public void setIsSecurity(boolean isSecurity) {
		mIsSecurity = isSecurity;
	}
}
