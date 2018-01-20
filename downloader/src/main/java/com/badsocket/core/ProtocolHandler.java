package com.badsocket.core;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface ProtocolHandler {


	Protocols getProtocol();


	DownloadComponentFactory downloadComponentFactory();


	boolean isSupport(Protocols protocol);


}
