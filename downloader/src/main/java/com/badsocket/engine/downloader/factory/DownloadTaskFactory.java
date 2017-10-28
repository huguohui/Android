package com.badsocket.engine.downloader.factory;

import com.badsocket.engine.downloader.DownloadDescriptor;
import com.badsocket.engine.downloader.DownloadTask;
import com.badsocket.engine.downloader.InternetDownloader;
import com.badsocket.engine.downloader.ProtocolHandler;
import com.badsocket.engine.downloader.UniversalDownloadTask;

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
