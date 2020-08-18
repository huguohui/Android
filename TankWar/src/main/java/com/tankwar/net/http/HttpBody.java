package com.tankwar.net.http;

import com.tankwar.net.Body;

import java.io.InputStream;
import java.io.Reader;

/**
 * Describe a HTTP content body.
 *
 * @author HGH
 * @since 2015/11/05
 */
public class HttpBody extends Body {
	/**
	 * Construct a HTTP body from string.
	 *
	 * @param body The content of body.
	 */
	public HttpBody(String body) {
		super(body);
	}

	/**
	 * Fetch content from input stream.
	 *
	 * @param source Content source
	 */
	@Override
	public void setContent(InputStream source) {

	}

	/**
	 * Fetch content from reader.
	 *
	 * @param source Content source
	 */
	@Override
	public void setContent(Reader source) {

	}

	/**
	 * Fetch content from source.
	 *
	 * @param source Content source
	 */
	@Override
	public void setContent(String source) {

	}

	/**
	 * Construct a HTTP body from input stream.
	 *
	 * @param body The input stream of contains content.
	 */
	public HttpBody(InputStream body) {
		super(body);
	}

}
