package com.xstream.manager.factory;

import com.xstream.engine.TaskInfo;
import com.xstream.engine.Descriptor;
import com.xstream.engine.downloader.DownloadTask;

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


	public abstract DownloadTask create(Descriptor d);


	public abstract DownloadTask create(TaskInfo d);
}
