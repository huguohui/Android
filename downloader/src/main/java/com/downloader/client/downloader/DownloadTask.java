package com.downloader.client.downloader;

import com.downloader.engine.Task;

/**
 * A task for downloading.
 * @since 2016/12/26 15:46
 */
public abstract class DownloadTask extends Task {
	protected DownloadTaskInfo info;

	protected DownloadTaskDescriptor descriptor;


	public DownloadTask(DownloadTaskDescriptor d) {
		descriptor = d;
		info = new DownloadTaskInfo();
	}
}
