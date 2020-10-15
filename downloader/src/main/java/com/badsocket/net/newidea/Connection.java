package com.badsocket.net.newidea;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public interface Connection {

	void close() throws Exception;

	boolean available();

//	InetSocketAddress getOutboundSocketAddress();
//
//	InetSocketAddress getInboundSocketAddress();

	long getEstablishedTimestamp();

	int read(ByteBuffer buffer) throws Exception;

	void write(ByteBuffer buffer) throws Exception;

}
