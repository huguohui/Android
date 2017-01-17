package com.downloader.client;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.DownloadTask;
import com.Protocol;
import com.downloader.http.HttpReceiver;
import com.downloader.http.HttpRequest;
import com.downloader.util.StringUtil;


/**
 * Tool for management download task.
 * @since 2016/12/26 15:45
 */
public class DownloadTaskManager {
	/** Download queue. */
	private List<DownloadTask> mQueue = new LinkedList<DownloadTask>();
	
	/** Instance of manager. */
	private static DownloadTaskManager mInstance = null;
	

	/** Supports protocols. */
	private final String[] protocols = {
		Protocol.HTTP,
		Protocol.HTTPS
	};
	
	
	/**
	 * Private constructor for singleton pattern.
	 */
	private DownloadTaskManager() {
//		switch(StringUtil.contains(protocols, mUrl.getProtocol())) {
//			case 0:
//				new HttpReceiver(new HttpRequest(mUrl));
//				break;
//		}
	}
	
	
	public void addTask(DownloadTask task) {
		if (task == null)
			throw new RuntimeException("The task is null!");
		
		mQueue.add(task);
	}
	
	
	public void startTask(int id) throws IOException {
		DownloadTask dt = mQueue.get(id);
		if (dt == null)
			throw new RuntimeException("The specail task not exists!");
		
		//dt.start();
	}
	
	
	/**
	 * Gets instance of manager.
	 * @return Instance of manager.
	 */
	public final synchronized static DownloadTaskManager getInstance() {
		if (mInstance == null)
			mInstance = new DownloadTaskManager();
		
		return mInstance;
	}
	
}
