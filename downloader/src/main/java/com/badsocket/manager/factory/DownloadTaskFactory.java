package com.badsocket.manager.factory;

import com.badsocket.engine.TaskInfo;
import com.badsocket.engine.Descriptor;
import com.badsocket.engine.downloader.DownloadTask;

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
