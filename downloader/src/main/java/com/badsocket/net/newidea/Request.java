package com.badsocket.net.newidea;

public abstract class Request {

	private URI uri;

	public Request(URI uri) {
		this.uri = uri;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}


}
