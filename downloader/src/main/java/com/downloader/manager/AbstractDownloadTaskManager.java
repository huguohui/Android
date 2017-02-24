package com.downloader.manager;

import com.downloader.base.Controlable;
import com.downloader.base.AbstractDownloadTask;

/**
 * Abstracts for manager of download task.
 */
public abstract class AbstractDownloadTaskManager extends AbstractManager<AbstractDownloadTask> implements Controlable {
	/**
	 * Create a download task by task descriptor.
	 * @param desc Task descriptor.
	 * @return Download task instance.
	 * @throws Throwable When exception occured.
	 */
	public abstract AbstractDownloadTask create(DownloadTaskDescriptor desc) throws Throwable;
}
