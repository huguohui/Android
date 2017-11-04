package com.badsocket.engine.downloader;


import com.badsocket.engine.Controlable;
import com.badsocket.engine.Monitor;
import com.badsocket.engine.MonitorWatcher;
import com.badsocket.engine.Protocols;

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


	void setParallelTaskNum(int num);


	int getParallelTaskNum();


	interface OnDownloadStartListener {
		void onDownloadStart(AbstractDownloader d);
	}


	interface OnDownloadFinishListener {
		void onDownloadFinish(AbstractDownloader d);
	}
}
