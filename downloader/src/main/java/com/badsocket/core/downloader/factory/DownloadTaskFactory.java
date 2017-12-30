package com.badsocket.core.downloader.factory;

import com.badsocket.core.DownloadTask;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.downloader.DownloadDescriptor;
import com.badsocket.core.downloader.InternetDownloader;

import java.io.IOException;

/**
 * Created by skyrim on 2017/10/6.
 */

public abstract class DownloadTaskFactory  {


	public static DownloadTask create(DownloadDescriptor d, ProtocolHandler handler,
									  InternetDownloader.ThreadAllocStategy policy) throws IOException {
		return /*new GenericDownloadTask(d, handler, policy)*/ null;
	}


}
