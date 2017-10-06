package com.downloader.engine.downloader;

import com.downloader.engine.Task;

/**
 * A task for downloading.
 * @since 2016/12/26 15:46
 */
public abstract class DownloadTask extends Task {

	protected HttpDownloadTaskInfo info;

	protected DownloadDescriptor descriptor;


	public DownloadTask(DownloadDescriptor d) {
		descriptor = d;
	}

}
