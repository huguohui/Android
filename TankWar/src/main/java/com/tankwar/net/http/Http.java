package com.tankwar.net.http;

import java.io.IOException;
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
	private URL url;

	
	/**
	 * A special HTTP version.
	 */
	private String version;


	/**
	 * HTTP header.
	 */
	private HttpHeader header;

	
	/**
	 * HTTP body.
	 */
	private HttpBody body;

    /**
	 * Is security HTTP?
	 */
	private boolean isSecurity;
	
	
	/**
	 * Construct a HTTP object by pass a URL.
	 * @param url URL
	 */
	public Http(URL url) {
		this.url = url;
	}


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


    public boolean isSecurity() {
        return isSecurity;
    }

    public void setIsSecurity(boolean isSecurity) {
        this.isSecurity = isSecurity;
    }
}
