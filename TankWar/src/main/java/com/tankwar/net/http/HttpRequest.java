package com.tankwar.net.http;

import com.tankwar.net.Request;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * HTTP request class implement.
 * @author HGH
 * @since 2015/11/05
 */
public class HttpRequest extends Http implements Request {

	public HttpRequest(URL url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	/**
	 * HTTP request sender.
	 */
	@Override
	public void sendRequest() {
	}

	@Override
	public boolean connect() throws SocketException, SocketTimeoutException,
			SocketTimeoutException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean connect(URL url) throws SocketException,
			SocketTimeoutException, SocketTimeoutException {
		// TODO Auto-generated method stub
		return false;
	}


	
}
