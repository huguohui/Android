package com.badsocket.net.newidea;


/**
 *  A client that can send request for some protocol.
 * @since 2019/01/24
 */
public interface Client {

	Response send(Request request);

}
