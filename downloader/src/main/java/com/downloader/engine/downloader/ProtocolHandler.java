package com.downloader.engine.downloader;

import com.downloader.engine.downloader.factory.DownloadTaskInfoFactory;
import com.downloader.net.SocketFamilyFactory;

/**
 * Created by skyrim on 2017/10/6.
 */

public interface ProtocolHandler {


	SocketFamilyFactory socketFamilyFactory();


	DownloadTaskInfoFactory downloadTaskInfoFactory();


}
