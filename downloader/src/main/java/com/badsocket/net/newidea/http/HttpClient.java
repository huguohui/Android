package com.badsocket.net.newidea.http;

import com.badsocket.net.newidea.AbstractClient;
import com.badsocket.net.newidea.Client;
import com.badsocket.net.newidea.Connection;
import com.badsocket.net.newidea.Request;
import com.badsocket.net.newidea.Response;
import com.badsocket.net.newidea.URI;

public class HttpClient extends AbstractClient {

	@Override
	public void close() throws Exception {

	}

	@Override
	public Connection open(URI uri) throws Exception {
		return null;
	}

	@Override
	public void send(Request request) throws Exception {

	}

	@Override
	public Response receive() throws Exception {
		return null;
	}
}
