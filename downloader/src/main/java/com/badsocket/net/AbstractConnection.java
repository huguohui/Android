package com.badsocket.net;

import com.badsocket.net.newidea.Connection;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;

public abstract class AbstractConnection implements Connection {

	long establishStamp;

	ByteChannel channel;

	protected AbstractConnection(ByteChannel channel) {
		this.channel = channel;
		establishStamp = System.currentTimeMillis();
	}

	@Override
	public void close() throws Exception {
		channel.close();
	}

	@Override
	public long getEstablishedTimestamp() {
		return establishStamp;
	}

	@Override
	public int read(ByteBuffer buffer) throws Exception {
		return channel.read(buffer);
	}

	@Override
	public void write(ByteBuffer buffer) throws Exception {
		channel.write(buffer);
	}
}
