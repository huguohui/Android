package com.downloader.http;

import com.downloader.base.AbstractTaskInfo;
import com.downloader.base.DownloadTask;
import com.downloader.manager.DownloadTaskDescriptor;


public class HttpDownloadTask extends DownloadTask {
	public HttpDownloadTask(DownloadTaskDescriptor d) {
		super(d);
	}


	protected void readDesc() {
		info.setUrl(descriptor.getUrl());
	}


	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {

	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {

	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume() throws Exception {

	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() throws Exception {

	}


	/**
	 * Get information of current task.
	 *
	 * @return Information of current task.
	 */
	@Override
	public AbstractTaskInfo info() {
		return null;
	}
}
