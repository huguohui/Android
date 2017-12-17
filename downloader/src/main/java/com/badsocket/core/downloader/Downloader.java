package com.badsocket.core.downloader;


import com.badsocket.core.Controlable;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.Monitor;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Protocols;

import java.io.IOException;
import java.util.List;

/**
 * @since 2017/10/5 11:03.
 */
public interface Downloader extends Controlable {
	/**
	 * Create a new download task.
	 * @param desc Descriptor.
	 */
	DownloadTask newTask(DownloadDescriptor desc) throws IOException;


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