package com.badsocket.net;


import java.io.Closeable;

/**
 * Response for requesting.
 */
public abstract class Response implements Closeable {

	protected Header header;

	protected Entity entity;


	/**
	 * Get response from request.
	 * @param r Request object.
	 */
	protected Response(Request r) {

	}


	public Header getHeader() {
		return header;
	}


	public Entity getEntity() {
		return entity;
	}
}
