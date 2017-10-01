package com.downloader.engine.downloader;


import java.io.IOException;

public class HttpDownloadTask extends DownloadTask {
	protected DownloadTaskInfo info;

	protected HttpDownloader downloader;


	public HttpDownloadTask(DownloadTaskDescriptor d) {
		super(d);
		init();
	}


	public HttpDownloadTask(DownloadTaskInfo info) {
		this((DownloadTaskDescriptor) null);
		this.info = info;
	}


	protected void init() {
		downloader = info != null ? new HttpDownloader(info) : new HttpDownloader(descriptor.getUrl());
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
	public void resume() throws IOException {
		downloader.resume();
	}


	/**
	 * Controls the task stop.
	 */
	@Override
	public void stop() throws IOException {
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
