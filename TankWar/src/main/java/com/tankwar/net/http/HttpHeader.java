package com.tankwar.net.http;


import com.tankwar.net.Header;

import java.util.Map;

/**
 * Describe a HTTP header. This header maybe 
 * is request header or response header.
 * 
 * @author HGH
 * @since 2015/11/05
 */
public class HttpHeader extends Header {
	/**
	 * A HTTP header, it maybe request header,
	 * or response header.
	 */
	protected HttpHeader header;


	/**
	 * A HTTP body.
	 */
	protected HttpBody body;


	public HttpHeader(Map<String, String> header) {
		super(header);
	}
}
