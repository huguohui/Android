package com.downloader.net.http;


import com.downloader.net.AbstractRequest;

import java.io.IOException;

public abstract class HttpResponseFactory  {
	/**
	 * Fetch info from given url.
	 * @throws IOException
	 */
	public static HttpResponse fetchResponseByUrl() throws IOException {
		HttpRequest httpRequest;
		HttpResponse httpResponse;

		httpRequest = buildHttpRequest(url, Http.Method.HEAD, false);
		httpRequest.setHeader(Http.RANGE, new AbstractRequest.Range(0).toString());
		httpRequest.send();
		return httpRequest.response();
	}
}
