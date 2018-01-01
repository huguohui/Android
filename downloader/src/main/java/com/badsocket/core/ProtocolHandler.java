package com.badsocket.core;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface ProtocolHandler {


	Protocol getProtocol();


	DownloadComponentFactory downloadComponentFactory();


	boolean isSupport(Protocol protocol);


}
