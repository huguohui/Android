package com.downloader.http;

import com.downloader.base.AbstractDownloader;

import java.io.IOException;
import java.net.URL;

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
	 * Prepare to start receiving data.
	 * @throws IOException
	 */
	private void fetchInfo() throws IOException {

	}
}
