package com.downloader.base;


/**
 * Response for requesting.
 */
public abstract class Response {
	/**
	 * Get response from request.
	 * @param r Request object.
	 */
	public Response(Request r) {
	}


	public abstract Object info();
}
