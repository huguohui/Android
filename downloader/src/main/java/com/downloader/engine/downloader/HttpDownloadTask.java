package com.downloader.engine.downloader;


import java.io.IOException;

public class HttpDownloadTask extends DownloadTask {
	protected HttpDownloadTaskInfo info;

	protected HttpDownloader downloader;


	public HttpDownloadTask(DownloadDescriptor d) {
		super(d);
		init();
	}


	public HttpDownloadTask(HttpDownloadTaskInfo info) {
		this((DownloadDescriptor) null);
		this.info = info;
	}


	protected void init() {
		downloader = info != null ? new HttpDownloader(info) : new HttpDownloader(descriptor);
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
	public HttpDownloadTaskInfo info() {
		return (HttpDownloadTaskInfo) downloader.getInfo();
	}


	/**
	 * To do some work.
	 */
	@Override
	public void work() throws Exception {

	}
}
