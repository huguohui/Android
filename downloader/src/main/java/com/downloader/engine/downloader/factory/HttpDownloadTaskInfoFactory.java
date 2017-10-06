package com.downloader.engine.downloader.factory;

import com.downloader.engine.downloader.DownloadDescriptor;
import com.downloader.engine.downloader.DownloadTaskInfo;
import com.downloader.engine.downloader.HttpDownloadTaskInfo;
import com.downloader.net.SocketResponse;
import com.downloader.net.http.HttpResponse;

/**
 * Created by skyrim on 2017/10/6.
 */

public class HttpDownloadTaskInfoFactory implements DownloadTaskInfoFactory {

	@Override
	public DownloadTaskInfo create(DownloadDescriptor rp) {
		return new HttpDownloadTaskInfo(rp);
	}

}
