package com.downloader.manager.factory;

import com.downloader.engine.AbstractTaskInfo;
import com.downloader.engine.downloader.AbstractDescriptor;
import com.downloader.engine.downloader.DownloadTask;

/**
 * @since 2017/10/3.
 */
public abstract class DownloadTaskFactory extends TaskFactory {
	/**
	 * To creating a object.
	 *
	 * @return A object.
	 */
	@Override
	public abstract DownloadTask create();


	public abstract DownloadTask create(AbstractDescriptor d);


	public abstract DownloadTask create(AbstractTaskInfo d);
}
