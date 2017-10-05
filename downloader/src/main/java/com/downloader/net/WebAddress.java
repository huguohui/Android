package com.downloader.net;

import com.downloader.util.UrlUtil;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * @since 2017/10/5 11:28.
 */
public class WebAddress extends InetSocketAddress {

	protected URL url;


	public WebAddress(URL url) {
		super(url.getHost(), url.getPort());
		this.url = url;
	}


	public URL getUrl() {
		return url;
	}


	public WebAddress setUrl(URL url) {
		this.url = url;
		return this;
	}
}
