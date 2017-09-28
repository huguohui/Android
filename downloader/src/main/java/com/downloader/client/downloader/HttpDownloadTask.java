package com.downloader.client.downloader;


public class HttpDownloadTask extends DownloadTask {
	protected DownloadTaskInfo info;

	protected HttpDownloader downloader;


	public HttpDownloadTask(DownloadTaskDescriptor d) {
		super(d);
		init();
	}


	protected void init() {
		info = new DownloadTaskInfo();
		info.setUrl(descriptor.getUrl());
		info.setThreadNumber(descriptor.getMaxThread());
		downloader = new HttpDownloader(info.getUrl());
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
	public void resume()  {

	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() {

	}


	/**
	 * Get information of current task.
	 *
	 * @return Information of current task.
	 */
	@Override
	public DownloadTaskInfo info() {
		return info;
	}
}
