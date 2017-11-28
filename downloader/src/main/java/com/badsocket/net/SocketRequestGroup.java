package com.badsocket.net;

import java.net.Socket;

/**
 * Created by skyrim on 2017/11/7.
 */
public interface SocketRequestGroup {


	void addRequest(SocketRequest req);


	SocketRequest removeRequest(SocketRequest req);


	SocketRequest[] getRequests();


	SocketRequest getRequest(int idx);


	void closeAll();



}
