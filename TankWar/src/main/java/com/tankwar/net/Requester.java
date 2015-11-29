package com.tankwar.net;

import java.net.InetSocketAddress;

/**
 * Describe a request.
 * @author HGH
 * @since 2015/11/04
 */
public interface Requester extends Sender {
	/**
	 * Open a connection.
	 */
	boolean open();


    /**
     * Open a connection and prepare to send request.
     * @return If connect success, return true else false.
     */
     boolean open(InetSocketAddress address);


    /**
     * Open a connection with timeout then prepare to send request.
     */
     boolean open(InetSocketAddress address, int timeout);


    /**
     * Close http connection.
     */
     void close();
}
