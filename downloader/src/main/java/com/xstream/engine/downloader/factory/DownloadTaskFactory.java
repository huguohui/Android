package com.xstream.engine.downloader.factory;

import com.xstream.engine.downloader.DownloadDescriptor;
import com.xstream.engine.downloader.DownloadTask;
import com.xstream.engine.downloader.InternetDownloader;
import com.xstream.engine.downloader.ProtocolHandler;
import com.xstream.engine.downloader.UniversalDownloadTask;

import java.io.IOException;

/**
 * Created by skyrim on 2017/10/6.
 */

public abstract class DownloadTaskFactory  {


	public static DownloadTask create(DownloadDescriptor d, ProtocolHandler handler,
				InternetDownloader.ThreadAllocPolicy policy) throws IOException {
		return new UniversalDownloadTask(d, handler, policy);
	}


}
