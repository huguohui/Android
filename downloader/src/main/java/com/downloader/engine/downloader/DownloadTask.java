package com.downloader.engine.downloader;

import com.downloader.engine.Controlable;
import com.downloader.engine.Task;

/**
 * A task for downloading.
 * @since 2016/12/26 15:46
 */
public abstract class DownloadTask extends Task implements Controlable {

	protected HttpDownloadTaskInfo info;

	protected DownloadDescriptor descriptor;


	public DownloadTask(DownloadDescriptor d) {
		descriptor = d;
	}


	public DownloadTask() {

	}


	public void start() throws Exception {
		state = State.initing;
	}


	public void stop() throws Exception {
		state = State.stoped;
	}


	public void pause() throws Exception {
		state = State.paused;
	}


	public void resume() throws Exception {
		state = State.resuming;
	}
}
