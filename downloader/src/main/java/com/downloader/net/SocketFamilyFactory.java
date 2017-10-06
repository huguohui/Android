package com.downloader.net;


import com.downloader.engine.downloader.DownloadDescriptor;
import com.downloader.engine.downloader.DownloadTaskInfo;
import com.downloader.engine.downloader.InternetDownloader;
import com.downloader.io.writer.Writer;

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
