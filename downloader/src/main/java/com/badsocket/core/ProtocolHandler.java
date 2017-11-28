package com.badsocket.core;

import com.badsocket.core.downloader.factory.DownloadTaskInfoFactory;
import com.badsocket.net.SocketFamilyFactory;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface ProtocolHandler {


	SocketFamilyFactory socketFamilyFactory();


	DownloadTaskInfoFactory downloadTaskInfoFactory();


}
