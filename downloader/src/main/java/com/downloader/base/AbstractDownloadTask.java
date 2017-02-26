package com.downloader.base;

import com.downloader.manager.AbstractDescriptor;

import java.net.URL;

/**
 * A task for downloading.
 * @since 2016/12/26 15:46
 */
public abstract class AbstractDownloadTask extends AbstractTask {
	/**
	 * Create a task by special task descriptor.
	 *
	 * @param desc Task descriptor.
	 */
	public AbstractDownloadTask(AbstractDescriptor desc) {
		super(desc);
	}


	/**
	 * Create a task with name.
	 *
	 * @param name Task name.
	 */
	public AbstractDownloadTask(String name) {
		super(name);
	}
}
