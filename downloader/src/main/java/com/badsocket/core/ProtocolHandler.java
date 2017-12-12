package com.badsocket.core;

import com.badsocket.core.downloader.factory.DownloadTaskInfoFactory;
import com.badsocket.net.SocketComponentFactory;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface ProtocolHandler {


	SocketComponentFactory socketFamilyFactory();


	DownloadTaskInfoFactory downloadTaskInfoFactory();


}
