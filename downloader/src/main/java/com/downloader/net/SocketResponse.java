package com.downloader.net;


import java.io.Closeable;

/**
 * SocketResponse for requesting.
 */
public abstract class SocketResponse implements Closeable {

	protected SocketHeader header;

	protected SocketEntity entity;


	/**
	 * Get response from request.
	 * @param r SocketRequest object.
	 */
	public SocketResponse(SocketRequest r) {

	}


	public SocketHeader getHeader() {
		return header;
	}


	public SocketEntity getEntity() {
		return entity;
	}
}
