package com.badsocket.core.downloader;


import com.badsocket.core.Context;
import com.badsocket.core.Controlable;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.Monitor;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.Protocol;
import com.badsocket.core.ProtocolHandler;

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
	DownloadTask newTask(DownloadTaskDescriptor desc) throws Exception;


	DownloadTask findTask(int id);


	void addTask(DownloadTask t) throws Exception;


	void deleteTask(int id);


	void startTask(int id);


	List<DownloadTask> taskList();


	List<Thread> threadList();


	boolean isStoped();


	boolean isRunning();


	boolean isPaused();


	void addWatcher(MonitorWatcher w);


	Monitor getMonitor();


	void addProtocolHandler(Protocol ptl, ProtocolHandler ph);


	ProtocolHandler getProtocolHandler(Protocol ptl);


	void setParallelTaskNum(int num);


	int getParallelTaskNum();


	Context getDownloaderContext();


	InternetDownloader.ThreadAllocStategy getThreadAllocStategy();


	void setThreadAllocStategy(InternetDownloader.ThreadAllocStategy stategy);


	interface OnDownloadStartListener {
		void onDownloadStart(AbstractDownloader d);
	}


	interface OnDownloadFinishListener {
		void onDownloadFinish(AbstractDownloader d);
	}
}
