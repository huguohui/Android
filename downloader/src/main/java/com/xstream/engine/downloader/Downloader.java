package com.xstream.engine.downloader;


import com.xstream.engine.Controlable;
import com.xstream.engine.Monitor;
import com.xstream.engine.MonitorWatcher;
import com.xstream.engine.Protocols;

import java.util.List;

/**
 * @since 2017/10/5 11:03.
 */
public interface Downloader extends Controlable {
	/**
	 * Create a new download task.
	 * @param desc Descriptor.
	 */
	DownloadTask newTask(DownloadDescriptor desc) throws Exception;


	DownloadTask findTask(int id);


	void addTask(DownloadTask t);


	void deleteTask(int id);


	void startTask(int id);


	List<DownloadTask> taskList();


	List<Thread> threadList();


	boolean isStoped();


	boolean isRunning();


	boolean isPaused();


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
