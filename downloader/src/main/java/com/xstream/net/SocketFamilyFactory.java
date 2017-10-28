package com.xstream.net;


import com.xstream.engine.downloader.DownloadDescriptor;
import com.xstream.engine.downloader.DownloadTaskInfo;
import com.xstream.engine.downloader.InternetDownloader;
import com.xstream.io.writer.Writer;

import java.io.IOException;
import java.net.SocketAddress;

public interface SocketFamilyFactory {


	SocketRequest createRequest(SocketAddress d) throws IOException;


	SocketRequest createRequest(DownloadDescriptor d) throws IOException;


	SocketRequest createRequest(DownloadTaskInfo i) throws IOException;


	SocketRequest createRequest(DownloadDescriptor d, SocketRequest.Range r) throws IOException;


	SocketRequest createRequest(DownloadTaskInfo i, SocketRequest.Range r) throws IOException;


	SocketRequest[] createRequest(DownloadTaskInfo i, InternetDownloader.ThreadAllocPolicy policy) throws IOException;


	SocketReceiver createReceiver(SocketRequest r, Writer w) throws IOException;


}
