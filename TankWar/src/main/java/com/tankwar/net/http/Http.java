package com.tankwar.net.http;

import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
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
	protected URL url;

	
	/**
	 * A special HTTP version.
	 */
	protected String version;


	/**
	 * HTTP header.
	 */
	protected HttpHeader header;

	
	/**
	 * HTTP body.
	 */
	protected HttpBody body;


	/**
	 * Is security HTTP?
	 */
	protected boolean isSecurity;
	
	
	/**
	 * Construct a HTTP object by pass a URL.
	 * @param url URL
	 */
	public Http(URL url) {
		this.url = url;
	}


	/**
	 * Open a HTTP connection, when success return true,
	 * if error, some exception will throws.
	 * @return If connect success, return true else false.
	 */
	public abstract boolean connect() throws SocketException,
			SocketTimeoutException, SocketTimeoutException;


	/**
	 * Open a HTTP connection by a special URL.
	 * @param url The special URL.
	 * @return If connect success, return true else false.
	 */
	public abstract boolean connect(URL url) throws SocketException,
			SocketTimeoutException, SocketTimeoutException;


	public URL getUrl() {
		return url;
	}


	public void setUrl(URL url) {
		this.url = url;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public HttpHeader getHeader() {
		return header;
	}


	public void setHeader(HttpHeader header) {
		this.header = header;
	}


	public HttpBody getBody() {
		return body;
	}


	public void setBody(HttpBody body) {
		this.body = body;
	}
}
