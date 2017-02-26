package com.downloader.http;

import com.downloader.base.AbstractDownloadTask;
import com.downloader.base.AbstractTaskInfo;
import com.downloader.base.DownloadTaskInfo;

/**
 * Download task of http.
 */
public class HttpDownloadTask extends AbstractDownloadTask {
	/** Download task information. */
	private DownloadTaskInfo mTaskInfo = new DownloadTaskInfo("");


	/**
	 * Constructor for creating task with name.
	 *
	 * @param name Name of task.
	 */
	public HttpDownloadTask(String name) {
		super(name);
	}


	/**
	 * Get the progress of task. value: 0 ~ 100
	 *
	 * @return Progress of task.
	 */
	@Override
	public int progress() {
		return 0;
	}

	/**
	 * Get information of current task.
	 *
	 * @return Information of current task.
	 */
	@Override
	public AbstractTaskInfo info() {
		return mTaskInfo;
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
}
