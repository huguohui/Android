package com.downloader.net;


import java.io.Closeable;

/**
 * Response for requesting.
 */
public abstract class Response implements Closeable {
	/**
	 * Get response from request.
	 * @param r Request object.
	 */
	public Response(Request r) {
	}


	public abstract Object info();
}
