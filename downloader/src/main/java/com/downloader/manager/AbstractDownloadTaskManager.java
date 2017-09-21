package com.downloader.manager;

import com.downloader.net.Controlable;
import com.downloader.net.DownloadTask;

/**
 * Abstracts for manager of download task.
 */
public abstract class AbstractDownloadTaskManager extends AbstractManager<DownloadTask> implements Controlable {
	/**
	 * Create a download task by task descriptor.
	 * @param desc Task descriptor.
	 * @return Download task instance.
	 * @throws Throwable When exception occured.
	 */
	public abstract DownloadTask create(DownloadTaskDescriptor desc) throws Throwable;
}
