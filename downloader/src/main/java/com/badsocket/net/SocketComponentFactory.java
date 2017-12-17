package com.badsocket.net;


import com.badsocket.core.DownloadTask;
import com.badsocket.core.downloader.DownloadDescriptor;
import com.badsocket.core.downloader.InternetDownloader;
import com.badsocket.io.writer.Writer;

import java.io.IOException;
import java.net.SocketAddress;

public interface SocketComponentFactory {


	SocketRequest createRequest(SocketAddress d) throws IOException;


	SocketRequest createRequest(DownloadDescriptor d) throws IOException;


	SocketRequest createRequest(DownloadTask i) throws IOException;


	SocketRequest createRequest(DownloadDescriptor d, SocketRequest.Range r) throws IOException;


	SocketRequest createRequest(DownloadTask i, SocketRequest.Range r) throws IOException;


	SocketRequest[] createRequest(DownloadTask i, InternetDownloader.ThreadAllocStategy policy) throws IOException;


	SocketReceiver createReceiver(SocketRequest r, Writer w) throws IOException;


	DownloadTask creatTask(DownloadDescriptor ds) throws Exception;



}
