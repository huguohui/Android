package com.badsocket.engine.downloader.factory;

import com.badsocket.engine.downloader.DownloadDescriptor;
import com.badsocket.engine.downloader.DownloadTaskInfo;
import com.badsocket.engine.downloader.HttpDownloadTaskInfo;

/**
 * Created by skyrim on 2017/10/6.
 */

public class HttpDownloadTaskInfoFactory implements DownloadTaskInfoFactory {

	@Override
	public DownloadTaskInfo create(DownloadDescriptor rp) {
		return new HttpDownloadTaskInfo(rp);
	}

}
