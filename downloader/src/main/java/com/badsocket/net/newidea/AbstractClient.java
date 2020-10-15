package com.badsocket.net.newidea;

import com.badsocket.net.AbstractConnection;
import com.badsocket.util.URIUtils;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;
public abstract class AbstractClient implements Client {

	protected URI uri;

	protected Connection connection;

	protected InetSocketAddress address;

	protected SocketChannel channel;

	public AbstractClient() {

	}
	
	protected void analyzeURI(URI uri) throws Exception {
		if (uri.isAbsoulteURI()) {
			throw new RuntimeException("Not supported uri!");
		}

		address = URIUtils.socketAddressByUri(uri);
	}

	public Connection open(URI uri) throws Exception {
		analyzeURI(uri);
		channel = SocketChannel.open(address);
		return new ConnectionImpl(channel);
	}


	private class ConnectionImpl extends AbstractConnection {
		ConnectionImpl(ByteChannel channel) {
			super(channel);
		}

		@Override
		public boolean available() {
			return false;
		}
	}

}
