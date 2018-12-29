package com.badsocket.core.executor;

import com.badsocket.core.Task;
import com.badsocket.core.downloader.DownloaderContext;

import java.util.List;

public class HttpDownloadTaskExecutor extends AbstractDownloadTaskExecutor {

	public HttpDownloadTaskExecutor(DownloaderContext context) {
		super(context);
	}


	@Override
	public void pause(Task t) throws Exception {

	}


	@Override
	public void resume(Task t) throws Exception {

	}


	@Override
	public void stop(Task t) throws Exception {

	}


	@Override
	public void start(Task task) throws Exception {

	}


	@Override
	public void start(Task task, long delay) throws Exception {

	}


	@Override
	public boolean isDone(Task t) {
		return false;
	}


	@Override
	public void cancel(Task t) {

	}


	@Override
	public List<Task> tasks() {
		return null;
	}


	@Override
	public boolean remove(Task t) {
		return false;
	}
}
