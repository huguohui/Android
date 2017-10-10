package com.downloader.engine.downloader;

import com.downloader.engine.Controlable;
import com.downloader.engine.Monitor;
import com.downloader.engine.MonitorWatcher;
import com.downloader.engine.Protocols;

import java.util.List;

/**
 * @since 2017/10/5 11:03.
 */
public interface Downloader extends Controlable {
	/** The download states. */
	enum DownloadState {
		unstart, initing, preparing, downloading, paused, stoped, finished, exceptional
	}


	/**
	 * Create a new download task.
	 * @param desc Descriptor.
	 */
	void newTask(DownloadDescriptor desc) throws Exception;


	DownloadTask findTask(int id);


	void deleteTask(int id);


	void startTask(int id);


	List<DownloadTask> taskList();


	List<Thread> threadList();


	DownloadState state();


	void addWatcher(MonitorWatcher w);


	Monitor getMonitor();


	void addProtocolHandler(Protocols ptl, ProtocolHandler ph);


	interface OnDownloadStartListener {
		void onDownloadStart(AbstractDownloader d);
	}


	interface OnDownloadFinishListener {
		void onDownloadFinish(AbstractDownloader d);
	}
}
