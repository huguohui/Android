package com.badsocket.net.newidea;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

public interface Client {

	void close() throws Exception;

	Connection open(URI uri) throws Exception;

	void send(Request request) throws Exception;

	Response receive() throws Exception;


}
