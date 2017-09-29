package com.downloader.client.downloader;


import com.downloader.util.TimeUtil;

public class HttpDownloadTask extends DownloadTask {
	protected DownloadTaskInfo info;

	protected HttpDownloader downloader;


	public HttpDownloadTask(DownloadTaskDescriptor d) {
		super(d);
		init();
	}


	protected void init() {
		downloader = new HttpDownloader(descriptor.getUrl());
	}


	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {
		downloader.start();
	}


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {
		downloader.pause();
	}


	/**
	 * Controls the task resume.
	 */
	@Override
	public void resume()  {
		downloader.resume();
	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() {
		downloader.stop();
	}


	/**
	 * Get information of current task.
	 *
	 * @return Information of current task.
	 */
	@Override
	public DownloadTaskInfo info() {
		return (DownloadTaskInfo) downloader.getInfo();
	}
}
