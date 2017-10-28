package com.xstream.engine.downloader.factory;

import com.xstream.engine.downloader.DownloadDescriptor;
import com.xstream.engine.downloader.DownloadTaskInfo;
import com.xstream.engine.downloader.HttpDownloadTaskInfo;

/**
 * Created by skyrim on 2017/10/6.
 */

public class HttpDownloadTaskInfoFactory implements DownloadTaskInfoFactory {

	@Override
	public DownloadTaskInfo create(DownloadDescriptor rp) {
		return new HttpDownloadTaskInfo(rp);
	}

}
