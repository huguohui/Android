package com.badsocket.core.downloader;

import com.badsocket.core.DownloadComponentFactory;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Protocols;
import com.badsocket.core.downloader.factory.HttpDownloadComponentFactory;

/**
 * Created by skyrim on 2018/1/21.
 */

public class HttpProtocolHandler implements ProtocolHandler {

	@Override
	public Protocols getProtocol() {
		return Protocols.HTTP;
	}

	@Override
	public DownloadComponentFactory downloadComponentFactory() {
		return new HttpDownloadComponentFactory();
	}

	@Override
	public boolean isSupport(Protocols protocol) {
		return protocol.equals(Protocols.HTTP);
	}

}