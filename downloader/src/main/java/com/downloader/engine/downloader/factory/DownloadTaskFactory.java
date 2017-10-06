package com.downloader.engine.downloader.factory;

import com.downloader.engine.downloader.DownloadDescriptor;
import com.downloader.engine.downloader.DownloadTask;
import com.downloader.engine.downloader.InternetDownloader;
import com.downloader.engine.downloader.ProtocolHandler;
import com.downloader.engine.downloader.UniversalDownloadTask;
import com.downloader.engine.downloader.exception.UnsupportedProtocolException;

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
