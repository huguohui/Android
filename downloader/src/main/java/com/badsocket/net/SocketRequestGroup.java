package com.badsocket.net;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

/**
 * Created by skyrim on 2017/11/7.
 */
public interface SocketRequestGroup {


	void addRequest(SocketRequest req);


	void addRequest(Collection<? extends SocketRequest> rs);


	SocketRequest removeRequest(SocketRequest req);


	SocketRequest[] getRequests();


	SocketRequest getRequest(int idx);


	SocketRequestGroup getParent();


	int activeCount();


	void closeAll() throws IOException;


	boolean isRoot();

}
