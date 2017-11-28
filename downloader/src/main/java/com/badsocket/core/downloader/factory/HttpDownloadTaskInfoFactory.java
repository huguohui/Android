package com.badsocket.core.downloader.factory;

import com.badsocket.core.downloader.DownloadDescriptor;
import com.badsocket.core.downloader.DownloadTaskInfo;
import com.badsocket.core.downloader.HttpDownloadTaskInfo;

/**
 * Created by skyrim on 2017/10/6.
 */

public class HttpDownloadTaskInfoFactory implements DownloadTaskInfoFactory {

	@Override
	public DownloadTaskInfo create(DownloadDescriptor rp) {
		return new HttpDownloadTaskInfo(rp);
	}

}
