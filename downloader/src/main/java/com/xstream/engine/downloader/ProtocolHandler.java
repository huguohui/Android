package com.xstream.engine.downloader;

import com.xstream.engine.downloader.factory.DownloadTaskInfoFactory;
import com.xstream.net.SocketFamilyFactory;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface ProtocolHandler {


	SocketFamilyFactory socketFamilyFactory();


	DownloadTaskInfoFactory downloadTaskInfoFactory();


}
