package com.badsocket.core;

import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.downloader.InternetDownloader;
import com.badsocket.net.Request;
import com.badsocket.net.Response;
import com.badsocket.net.Receiver;
import com.badsocket.net.newidea.URI;

import java.io.IOException;

public interface DownloadComponentFactory {

	Request createRequest(URI d) throws IOException;

	Request createRequest(DownloadTaskDescriptor d) throws IOException;

	Request createRequest(Response i) throws IOException;

	Request createRequest(DownloadTask i) throws IOException;

	Request createRequest(DownloadTaskDescriptor d, Request.Range r) throws IOException;

	Request createRequest(DownloadTask i, Request.Range r) throws IOException;

	Request[] createRequest(DownloadTask r, InternetDownloader.ThreadAllocStategy stategy) throws IOException;

	Receiver createReceiver(Request r) throws IOException;

	DownloadTask creatDownloadTask(Downloader c, URI address) throws IOException;

	DownloadTask creatDownloadTask(Downloader c, DownloadTaskDescriptor d) throws IOException;

	DownloadTask creatDownloadTask(Downloader c, DownloadTaskDescriptor d, Response response) throws IOException;

}
