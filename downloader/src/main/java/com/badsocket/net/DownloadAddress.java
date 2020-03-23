package com.badsocket.net;

import java.net.InetSocketAddress;
import java.net.URL;

/**
 * @since 2017/10/5 11:28.
 */
public class DownloadAddress extends InetSocketAddress {

	protected URL url;

	protected String protocol;

	protected String address;

	public DownloadAddress(URL url) {
		super(url.getHost(), url.getPort() != -1 ? url.getPort() : 80);
		this.url = url;
		protocol = url.getProtocol();
	}

	public DownloadAddress(String address) {
		super("", 80);
		this.address = address;
	}

	public URL getUrl() {
		return url;
	}

	public DownloadAddress setUrl(URL url) {
		this.url = url;
		return this;
	}

	public String getProtocol() {
		return protocol;
	}
}
