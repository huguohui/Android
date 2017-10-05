package com.downloader.manager.factory;

import com.downloader.engine.TaskInfo;
import com.downloader.engine.downloader.Descriptor;
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
	public DownloadTask create(Descriptor d) {
		return new HttpDownloadTask((DownloadTaskDescriptor) d);
	}


	@Override
	public DownloadTask create(TaskInfo d) {
		return new HttpDownloadTask((DownloadTaskInfo) d);
	}
}
