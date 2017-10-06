package com.downloader.net;

import java.net.InetSocketAddress;
import java.net.URL;

/**
 * @since 2017/10/5 11:28.
 */
public class WebAddress extends InetSocketAddress {

	protected URL url;

	protected String protocol;


	public WebAddress(URL url) {
		super(url.getHost(), url.getPort() != - 1 ? url.getPort() : 80);
		this.url = url;
		protocol = url.getProtocol();
	}


	public URL getUrl() {
		return url;
	}


	public WebAddress setUrl(URL url) {
		this.url = url;
		return this;
	}


	public String getProtocol() {
		return protocol;
	}
}
