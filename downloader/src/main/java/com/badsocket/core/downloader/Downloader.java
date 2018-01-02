package com.badsocket.core.downloader;


import com.badsocket.core.Context;
import com.badsocket.core.Controlable;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.Monitor;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.Protocol;
import com.badsocket.core.ProtocolHandler;
import com.badsocket.core.Task;

import java.util.List;

/**
 * @since 2017/10/5 11:03.
 */
public interface Downloader extends Controlable {


	boolean isTaskExists(Task task);


	/**
	 * Create a new download task.
	 * @param desc Descriptor.
	 */
	DownloadTask newTask(DownloadTaskDescriptor desc) throws Exception;


	DownloadTask findTask(int idx);


	DownloadTask findTaskByTaskId(int idx);


	void addTask(DownloadTask t) throws Exception;


	void deleteTask(int id);


	void deleteTask(DownloadTask task);


	void startTask(int id) throws Exception;


	void startTask(DownloadTask task) throws Exception;


	void stopTask(int id) throws Exception;


	void stopTask(DownloadTask task) throws Exception;


	void pauseTask(int id) throws Exception;


	void pauseTask(DownloadTask task) throws Exception;


	void resumeTask(int id) throws Exception;


	void resumeTask(DownloadTask task) throws Exception;


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
