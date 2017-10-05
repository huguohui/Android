package com.downloader.engine.downloader;

import com.downloader.engine.Controlable;

/**
 * @since 2017/10/5 11:03.
 */
public interface Downloader extends Controlable {
	/** The download states. */
	enum State {
		unstart, initing, preparing, downloading, paused, stoped, finished, exceptional
	}


	interface OnDownloadStartListener {
		void onDownloadStart(AbstractDownloader d);
	}


	interface OnDownloadFinishListener {
		void onDownloadFinish(AbstractDownloader d);
	}
}
