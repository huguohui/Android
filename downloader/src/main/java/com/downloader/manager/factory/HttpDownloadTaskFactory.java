package com.downloader.manager.factory;

import com.downloader.engine.AbstractTaskInfo;
import com.downloader.engine.downloader.AbstractDescriptor;
import com.downloader.engine.downloader.DownloadTask;
import com.downloader.engine.downloader.DownloadTaskDescriptor;
import com.downloader.engine.downloader.DownloadTaskInfo;
import com.downloader.engine.downloader.HttpDownloadTask;

/**
 * @since 2017/10/3.
 */

public class HttpDownloadTaskFactory extends DownloadTaskFactory {
	/**
	 * To creating a object.
	 *
	 * @return A object.
	 */
	@Override
	public DownloadTask create() {
		return null;
	}


	@Override
	public DownloadTask create(AbstractDescriptor d) {
		return new HttpDownloadTask((DownloadTaskDescriptor) d);
	}


	@Override
	public DownloadTask create(AbstractTaskInfo d) {
		return new HttpDownloadTask((DownloadTaskInfo) d);
	}
}
