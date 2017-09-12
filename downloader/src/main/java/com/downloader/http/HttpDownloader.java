package com.downloader.http;

import java.io.IOException;
import java.net.URL;

import com.downloader.base.AbstractDownloader;

/**
 * Downloads data based http protocol.
 */
public class HttpDownloader extends AbstractDownloader {
	protected URL url;

	protected HttpResponse httpResponse;


	public HttpDownloader(URL url) throws NullPointerException {
		this.url = url;

	}


	public HttpDownloader(HttpResponse hr) throws NullPointerException, IOException {
		httpResponse = hr;
	}


	/**
	 * Fetch info from given url.
	 * @throws IOException
	 */
	private void fetchInfo() throws IOException {
		if (httpResponse != null) {
			mLength = httpResponse.getContentLength();
		}
	}
}
