package com.badsocket.engine.downloader;

import com.badsocket.engine.downloader.factory.DownloadTaskInfoFactory;
import com.badsocket.net.SocketFamilyFactory;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface ProtocolHandler {


	SocketFamilyFactory socketFamilyFactory();


	DownloadTaskInfoFactory downloadTaskInfoFactory();


}
