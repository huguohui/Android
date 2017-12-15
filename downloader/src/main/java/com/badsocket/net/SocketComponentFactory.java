package com.badsocket.net;


import com.badsocket.core.downloader.DownloadDescriptor;
import com.badsocket.core.downloader.DownloadTaskInfo;
import com.badsocket.core.downloader.InternetDownloader;
import com.badsocket.io.writer.Writer;

import java.io.IOException;
import java.net.SocketAddress;

public interface SocketComponentFactory {


	SocketRequest createRequest(SocketAddress d) throws IOException;


	SocketRequest createRequest(DownloadDescriptor d) throws IOException;


	SocketRequest createRequest(DownloadTaskInfo i) throws IOException;


	SocketRequest createRequest(DownloadDescriptor d, SocketRequest.Range r) throws IOException;


	SocketRequest createRequest(DownloadTaskInfo i, SocketRequest.Range r) throws IOException;


	SocketRequest[] createRequest(DownloadTaskInfo i, InternetDownloader.ThreadAllocStategy policy) throws IOException;


	SocketReceiver createReceiver(SocketRequest r, Writer w) throws IOException;


}
